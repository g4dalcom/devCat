package com.project.devcat.controller;

import com.project.devcat.dto.PostDto;
import com.project.devcat.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


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

    @GetMapping("/posts/post")
    public String createPost(Model model) {
        model.addAttribute("post", new PostDto.Request());
        return "createPost";
    }

}
