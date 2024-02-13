package com.myblog.blogapp.repositories;

import com.myblog.blogapp.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
