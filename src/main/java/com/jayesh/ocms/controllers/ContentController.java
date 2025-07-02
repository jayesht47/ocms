package com.jayesh.ocms.controllers;


import com.jayesh.ocms.entities.Content;
import com.jayesh.ocms.services.ContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/content")
public class ContentController {


    private static final Logger log = LoggerFactory.getLogger(ContentController.class);

    @Autowired
    ContentService contentService;

    @GetMapping("/hello")
    public String getHello() {
        UUID id = UUID.randomUUID();
        return id.toString();
    }

    @PostMapping("/add")
    public Content addContent(@RequestBody Content content) {
        log.info("Add content request for Content {}", content);
        return contentService.createContent(content);
    }

}
