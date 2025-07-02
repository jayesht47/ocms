package com.jayesh.ocms.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "Content")
@Getter
@Setter
public class Content {

    @Id
    @JsonIgnore
    @UuidGenerator
    private String id;

    @NotBlank
    @JsonProperty("title")
    private String title;

    @NotBlank
    @JsonProperty("author")
    private String author;

    @NotBlank
    @JsonProperty("body")
    private String body;

    @CreatedDate
    private LocalDateTime created;

    @LastModifiedDate
    @Column(name = "last_modified")
    private LocalDateTime modified;

    @JsonProperty("createdBy")
    @Column(name = "created_by")
    private String createdBy;

    @JsonProperty("lastModifiedBy")
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Override
    public String toString() {
        return "Content{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", body='" + body + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                '}';
    }
}
