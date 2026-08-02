package com.vipha.ecommerce.features.media;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medias")
@RequiredArgsConstructor

public class MediaController {

    private  final MediaService mediaService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public MediaResponse upload(@RequestPart MultipartFile file){
       return mediaService.upload(file);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/multiple")
    public List<MediaResponse> multipleUpload(@RequestPart List<MultipartFile> files){
        return mediaService.multipleUpload(files);
    }

    @GetMapping("/{name}")
    public MediaResponse findByName(@PathVariable String name){
        return mediaService.findByName(name);
    }

    @GetMapping
    public Page<MediaResponse> findAll(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                       @RequestParam(required = false, defaultValue = "10") int pageSize){
        return mediaService.findAll(pageNumber, pageSize);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{name}")
    public void deleteByName(@PathVariable String name){
        mediaService.deleteByName(name);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{name}/is-draft")
    public void draftByName(@PathVariable String name){
       mediaService.deleteByName(name);
    }

}
