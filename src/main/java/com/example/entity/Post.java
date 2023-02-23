package com.example.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    private String content;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Comment> comments;

    public Post() {
    }

    public Post(String content, Set<Comment> comments) {
        this.content = content;
        this.comments = comments;
    }

    public Set<Comment> getComments() {
        return comments;
    }
}
