package com.project.devcat.controller;

import com.project.devcat.domain.Post;
import com.project.devcat.dto.PostDto;
import com.project.devcat.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


@RequiredArgsConstructor
@Controller
public class HomeController {

    private final PostRepository postRepository;

    /* 메인화면 */
    @GetMapping("/")
    public String main(Model model, @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable) {
        model.addAttribute("posts", postRepository.findAll(pageable));
        model.addAttribute("sortProperty", pageable.getSort().toString().contains("DESC") ? "desc" : "asc");
        model.addAttribute("keyword", "");
        return "index";
    }

    /* 상세 페이지 */
    @GetMapping("/posts/{post_id}")
    public String detailPost(@PathVariable Long post_id,
                             Model model) {
        Optional<Post> post = postRepository.findById(post_id);
        if (!post.isPresent()) {
            return "redirect:/";
        }
        model.addAttribute("post", post.get());
        return "detailPost";
    }

    /* 글 등록 페이지 */
    @GetMapping("/posts/post")
    public String createPost(Model model) {
        model.addAttribute("post", new PostDto.Request());
        return "createPost";
    }
}
