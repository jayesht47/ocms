package com.jayesh.ocms.services;

import com.jayesh.ocms.entities.Content;
import com.jayesh.ocms.exceptions.NotFoundException;

public interface ContentService {

    public Content createContent(Content content);

    public Content getContent(String contentId) throws NotFoundException;

}
