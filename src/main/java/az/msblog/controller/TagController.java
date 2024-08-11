package az.msblog.controller;


import az.msblog.dao.request.TagRequest;
import az.msblog.dao.response.TagResponse;
import az.msblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public void createTag(@RequestBody TagRequest tagRequest) {
        tagService.createTag(tagRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("{id}")
    public void updateTag(@PathVariable Long id, @RequestBody TagRequest tagRequest) {
        tagService.updateTag(id, tagRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
    }


}
