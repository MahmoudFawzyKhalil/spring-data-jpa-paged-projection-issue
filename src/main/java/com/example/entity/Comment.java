package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    private String content;

    public Comment() {
    }

    public Comment(String content) {
        this.content = content;
    }
}