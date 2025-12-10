package com.jayesh.ocms.services;

import com.jayesh.ocms.entities.Content;
import com.jayesh.ocms.exceptions.NotFoundException;
import com.jayesh.ocms.repositories.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    public ContentServiceImpl(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    private final ContentRepository contentRepository;

    @Override
    public Content createContent(Content content) {
        UUID contentId = UUID.randomUUID();
        content.setId(contentId.toString());
        return contentRepository.save(content);
    }

    @Override
    public Content getContent(String contentId) throws NotFoundException {
        Optional<Content> content = contentRepository.findById(contentId);
        if (content.isPresent())
            return content.get();
        else throw new NotFoundException("Content with provided Id not found");
    }
}
