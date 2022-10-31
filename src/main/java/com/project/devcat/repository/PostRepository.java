package com.project.devcat.repository;

import com.project.devcat.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByTitleIgnoreCaseContains(Pageable pageable, String keyword);
}
