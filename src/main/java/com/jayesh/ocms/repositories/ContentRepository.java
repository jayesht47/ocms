package com.jayesh.ocms.repositories;

import com.jayesh.ocms.entities.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ContentRepository extends JpaRepository<Content, String> {

}
