package com.jayesh.ocms.services;

import com.jayesh.ocms.entities.Content;
import com.jayesh.ocms.repositories.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    ContentRepository contentRepository;

    @Override
    public Content createContent(Content content) {
        return contentRepository.save(content);
    }
}
