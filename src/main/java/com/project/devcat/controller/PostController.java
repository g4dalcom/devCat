package com.project.devcat.controller;

import com.project.devcat.domain.Post;
import com.project.devcat.dto.PostDto;
import com.project.devcat.repository.PostRepository;
import com.project.devcat.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;


    /* 전체 조회 */
    @GetMapping("/posts")
    public String readPosts(Model model, String keyword, @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("posts", postRepository.findByTitleIgnoreCaseContains(pageable, keyword));
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortProperty", pageable.getSort().toString().contains("DESC") ? "desc" : "asc");

        return "index";
    }

    /* 게시글 등록 */
    @PostMapping("/posts/post")
    public String createPost(@ModelAttribute PostDto.Request request) {
        postService.createPost(request);
        log.info("request={}", request);

        return "redirect:/";
    }

    /* 게시글 수정하기 */
    @PutMapping("posts/{post_id}")
    public String updatePost(@PathVariable Long post_id, @ModelAttribute PostDto.Request request) {
        postService.updatePost(post_id, request);

        return "redirect:/";
    }


    /* 게시글 삭제 */
    @DeleteMapping("/posts/{post_id}")
    public String deletePost(@PathVariable Long post_id) {
        postRepository.deleteById(post_id);
        log.info("delete={}", post_id);

        return "redirect:/";
    }

    @PostConstruct
    public void init() {
        postRepository.save(new Post(2L, "제목", "콘텐츠", "잡담", 1));
        postRepository.save(new Post(3L, "제목", "콘텐츠", "질문", 1));
        postRepository.save(new Post(4L, "제목", "콘텐츠", "정보공유", 1));
    }
}
