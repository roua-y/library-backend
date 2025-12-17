package com.library.lab4sring_boot.controller;

import com.library.lab4sring_boot.model.Tag;
import com.library.lab4sring_boot.service.TagService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public Tag createTag(@Valid @RequestBody Tag tag) {
        return tagService.createTag(tag);
    }

    @GetMapping
    public List<Tag> getTags() {
        return tagService.getAllTags();
    }
}
