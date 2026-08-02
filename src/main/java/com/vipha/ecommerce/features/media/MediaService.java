package com.vipha.ecommerce.features.media;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {

    // upload single media
    MediaResponse upload(MultipartFile file);

    List<MediaResponse> multipleUpload(List<MultipartFile> files);

    MediaResponse findByName(String name);

    Page<MediaResponse> findAll(int pageNumber, int pageSize);

    void deleteByName(String name);

    void draftByName(String name);
}