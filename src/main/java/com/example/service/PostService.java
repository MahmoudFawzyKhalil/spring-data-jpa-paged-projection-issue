package com.example.service;

import com.example.entity.Comment;
import com.example.entity.Post;
import com.example.entity.QPost;
import com.example.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void createAPostWithTwoComments() {
        Post post = new Post("This is my first post!", Set.of(
                new Comment("Great post!"),
                new Comment("This is the worst thing I have read in my life")
        ));

        postRepository.save(post);
    }

    public Page<Post> getPageOfPostsContaining(String text) {
        text = "%" + text.toLowerCase() + "%";

        return postRepository.findBy(QPost.post.content.toLowerCase().like(text), q -> q
                .project("comments")
                .page(PageRequest.of(
                        0, 10)
                ));
    }

    public List<Post> getAllPostsContaining(String text) {
        text = "%" + text.toLowerCase() + "%";

        return postRepository.findBy(QPost.post.content.toLowerCase().like(text), q -> q
                .project("comments")
                .all());
    }
}
