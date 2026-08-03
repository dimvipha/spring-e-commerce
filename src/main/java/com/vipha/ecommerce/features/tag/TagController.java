package com.vipha.ecommerce.features.tag;

import com.vipha.ecommerce.features.tag.dto.TagRequest;
import com.vipha.ecommerce.features.tag.dto.TagResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    @GetMapping("/search")
    public List<TagResponse> search(@RequestParam(required = false, defaultValue = "") String name) {
        return tagService.search(name);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagResponse createTag(@Valid @RequestBody TagRequest request) {
        return tagService.createNew(request);
    }

    @GetMapping("/{id}")
    public TagResponse findById(@PathVariable Integer id) {
        return tagService.findById(id);
    }

    @GetMapping
    public Page<TagResponse> findAll(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize
    ) {
        Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortById);
        return tagService.findAllTag(pageable);
    }

    @PutMapping("/{id}")
    public TagResponse updateTag(@PathVariable Integer id, @Valid @RequestBody TagRequest request) {
        return tagService.updateById(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) {
        tagService.deleteById(id);
    }


}
