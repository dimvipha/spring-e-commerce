package com.vipha.ecommerce.features.media;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;

    @Value("${media.location}")
    private String mediaLocation;

    @Value("${media.client-path}")
    private String mediaClientPath;

    @Value("${media.base-uri}")
    private String mediaBaseUri;

    private final static String MB = "MB";

    @Override
    public void draftByName(String name){
        Media media=mediaRepository.findByName(name)
                .orElseThrow(()-> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Media has not been found "
                        )
                );
        media.setIsDraft(Boolean.TRUE);
        mediaRepository.save(media);
    }

    @Transactional
    @Override
    public void deleteByName(String name){
        Media media=mediaRepository.findByName(name)
                .orElseThrow(()-> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Media has not been found "
                        )
                );
        // validate if the draft is false cannot delete permanently
        if(!media.getIsDraft()){
            return;
        }

        // delete from database
        mediaRepository.delete(media);

        // delete from file system
        Path path =Paths.get(bultMediaPath(media.getName(),media.getExtension()));
        try {
            Files.delete(path);
        }catch (IOException e){
            log.info("error ", e.getMessage());
            throw  new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong"
            );
        }

    }

    @Override
    public MediaResponse findByName(String name){
        Media media=mediaRepository.findByName(name)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Media has not been found "
                        )
                );
        return buildMediaResponse(media);

    }

    @Override
    public Page<MediaResponse> findAll(int pageNumber, int pageSize){
        Sort sortById=Sort.by(Sort.Direction.DESC,"id");
        Pageable pageable= PageRequest.of(pageNumber, pageSize,sortById);
        return mediaRepository.findByIsDraft(pageable,Boolean.FALSE).map(this::buildMediaResponse);
    }


    @Override
    public MediaResponse upload(MultipartFile file) {
        // TODO
        // 1. Create path object (ផ្ទុកទីតាំង file)
        String name = UUID.randomUUID().toString();
        // e.g. Vital.png
        int lastIndexDot = file.getOriginalFilename().lastIndexOf('.');
        String extension = file.getOriginalFilename().substring(lastIndexDot + 1);
        Path path = Paths.get(mediaLocation + name + "." + extension);
        log.info("Uploading media location: {}", path);

        // 2. Copy file
        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Media has been uploaded failed"
            );
        }

        // 3. Save into database table
        Media media = new Media();
        media.setName(name);
        media.setExtension(extension);
        media.setSize((float) file.getSize());
        media.setMediaType(file.getContentType());
        media.setIsDraft(false);
        media = mediaRepository.save(media);

        return buildMediaResponse(media);
    }

    private MediaResponse buildMediaResponse(Media media) {
        // 1MB = 1_000_000B
        return MediaResponse.builder()
                .id(media.getId())
                .name(media.getName())
                .extension(media.getExtension())
                .mediaType(media.getMediaType())
                .size(media.getSize() / 1_000_000)
                .isDraft(media.getIsDraft())
                .measurement(MB)
                .uri(buildMediaUri(media)) // http://localhost:1333/media/78689a24-551c-4575-9831-a4ec8e2bb0ef.png
                .build();
    }

private String buildMediaUri(Media media) {
    return mediaBaseUri +
            mediaClientPath +
            "/" + media.getName() +
            "." + media.getExtension();
}

    @Transactional
    @Override
    public List<MediaResponse> multipleUpload(List<MultipartFile> files){
        return files.stream().map(this::upload).toList();
    }

    private String bultMediaPath( String mediaName, String mediaExtension){
        return mediaLocation+ mediaName+"."+mediaExtension;
    }
}
