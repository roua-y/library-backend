package com.library.lab4sring_boot.controller;

import com.library.lab4sring_boot.model.Publisher;
import com.library.lab4sring_boot.service.PublisherService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping
    public Publisher createPublisher(@Valid @RequestBody Publisher publisher) {
        return publisherService.createPublisher(publisher);
    }

    @GetMapping
    public List<Publisher> getPublishers() {
        return publisherService.getAllPublishers();
    }
}
