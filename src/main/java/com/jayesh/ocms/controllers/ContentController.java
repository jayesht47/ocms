package com.jayesh.ocms.controllers;


import com.jayesh.ocms.entities.Content;
import com.jayesh.ocms.exceptions.NotFoundException;
import com.jayesh.ocms.services.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/content")
public class ContentController {

    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    private final ContentService contentService;

    @PostMapping
    public Content addContent(@RequestBody Content content) {
        return contentService.createContent(content);
    }

    @GetMapping("/{contentId}")
    public Content getContent(@PathVariable("contentId") String contentId) throws NotFoundException {
        return contentService.getContent(contentId);
    }

}
