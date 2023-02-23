package com.example;

import com.example.entity.Post;
import com.example.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@SpringBootApplication
@EnableJpaRepositories
public class SpringDataJpaPagedProjectionIssueApplication implements ApplicationRunner {

    @Autowired
    private PostService postService;

    @Override
    public void run(ApplicationArguments args) {
        postService.createAPostWithTwoComments();
        List<Post> posts = postService.getAllPostsContaining("t");
        // Hibernate: select post0_.id as id1_1_0_, comment2_.id as id1_0_1_, post0_.content as content2_1_0_, comment2_.content as content2_0_1_, comments1_.post_id as post_id1_2_0__, comments1_.comments_id as comments2_2_0__ from post post0_ left outer join post_comments comments1_ on post0_.id=comments1_.post_id left outer join comment comment2_ on comments1_.comments_id=comment2_.id where lower(post0_.content) like ? escape '!'

        System.out.println(posts.get(0).getComments()); // Prints comments

        Page<Post> page = postService.getPageOfPostsContaining("t");
        // Hibernate: select post0_.id as id1_1_, post0_.content as content2_1_ from post post0_ where lower(post0_.content) like ? escape '!' limit ?
        System.out.println(page.getContent().get(0).getComments().size()); // Results in a lazy initialization exception
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaPagedProjectionIssueApplication.class, args);
    }

}
