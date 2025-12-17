package com.library.lab4sring_boot.service;

import com.library.lab4sring_boot.model.Tag;
import com.library.lab4sring_boot.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public long countTags() {
        return tagRepository.count();
    }
}
