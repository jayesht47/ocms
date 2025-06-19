package com.jayesh.ocms.controllers;


import com.jayesh.ocms.entities.Content;
import com.jayesh.ocms.services.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/content")
public class ContentController {


    @Autowired
    ContentService contentService;

    @GetMapping("/hello")
    public String getHello() {
        UUID id = UUID.randomUUID();
        return id.toString();
    }

    @PostMapping
    public Content addContent(@RequestBody Content content) {
        return contentService.createContent(content);
    }

}
