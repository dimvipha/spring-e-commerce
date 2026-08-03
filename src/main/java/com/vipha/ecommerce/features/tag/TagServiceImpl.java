package com.vipha.ecommerce.features.tag;

import com.vipha.ecommerce.features.tag.dto.TagRequest;
import com.vipha.ecommerce.features.tag.dto.TagResponse;
import com.vipha.ecommerce.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public List<TagResponse> search(String name) {
        return tagRepository.findByNameContainsIgnoreCase(name).stream()
                .map(tagMapper::mapTagToTagResponse).toList();
    }

    @Override
    public TagResponse createNew(TagRequest request) {
        // validate tag name (unique)
        if (tagRepository.existsByName(request.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tag name already exits");
        }

        Tag newTag = tagMapper.mapTagRequestToTag(request);
        newTag.setIsDeleted(false);

        newTag = tagRepository.save(newTag);

        return tagMapper.mapTagToTagResponse(newTag);
    }

    @Override
    public TagResponse findById(Integer id) {
        return tagRepository.findById(id).map(
                tagMapper::mapTagToTagResponse
        ).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "tag has not been found")
        );
    }

    @Override
    public Page<TagResponse> findAllTag(Pageable pageable) {
        Page<Tag> tags = tagRepository.findAll(pageable);

        return tags.map(tagMapper::mapTagToTagResponse);
    }

    @Override
    public TagResponse updateById(Integer id, TagRequest request) {
        // validate tag
        Tag tag = tagRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "tag has not been found")
        );

        // validate tag name (unique), skip when the name is unchanged
        if (!tag.getName().equals(request.name()) && tagRepository.existsByName(request.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tag name already exits");
        }

        tagMapper.toEntity(request, tag);
        tag = tagRepository.save(tag);

        return tagMapper.mapTagToTagResponse(tag);
    }

    @Override
    public void deleteById(Integer id) {
        // validate tag
        Tag tag = tagRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "tag has not been found")
        );
        tagRepository.delete(tag);
    }
}
