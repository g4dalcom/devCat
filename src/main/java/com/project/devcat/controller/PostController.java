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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;

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
    public String createPost(@ModelAttribute PostDto.Request request, @RequestPart(required = false) MultipartFile file) throws IOException {
        postService.createPost(request, file);
        log.info("request={}", request);

        return "redirect:/";
    }


    /* 게시글 수정하기 */
    @PutMapping("posts/{post_id}")
    public String updatePost(@PathVariable Long post_id, @ModelAttribute PostDto.Request request, Model model) {
        postService.updatePost(post_id, request);
        model.addAttribute("message", "수정이 완료되었습니다.");
        model.addAttribute("url", "/");

        return "message";
    }


    /* 게시글 삭제 */
    @DeleteMapping("/posts/{post_id}")
    public String deletePost(@PathVariable Long post_id, Model model) {
        postRepository.deleteById(post_id);
        log.info("delete={}", post_id);

        model.addAttribute("message", "삭제가 완료되었습니다.");
        model.addAttribute("url", "/");

        return "message";
    }


    @PostConstruct
    public void init() {
        postRepository.save(new Post(2L, "제목", "콘텐츠", "잡담", 1, null, null, null));
        postRepository.save(new Post(3L, "제목", "콘텐츠", "질문", 1, null, null, null));
        postRepository.save(new Post(4L, "제목", "콘텐츠", "정보공유", 1, null, null, null));
        postRepository.save(new Post(5L, "제목", "콘텐츠", "정보공유", 1, null, null, null));
        postRepository.save(new Post(6L, "제목", "콘텐츠", "정보공유", 1, null, null, null));
        postRepository.save(new Post(7L, "제목", "콘텐츠", "정보공유", 1, null, null, null));
        postRepository.save(new Post(8L, "제목", "콘텐츠", "정보공유", 1, null, null, null));
        postRepository.save(new Post(9L, "제목", "콘텐츠", "정보공유", 1, null, null, null));
        postRepository.save(new Post(10L, "제목", "콘텐츠", "정보공유", 1, null, null, null));
        postRepository.save(new Post(11L, "제목", "콘텐츠", "정보공유", 1, null, null, null));
    }
}
