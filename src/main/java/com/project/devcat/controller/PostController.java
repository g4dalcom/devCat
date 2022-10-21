package com.project.devcat.controller;

import com.project.devcat.dto.PostDto;
import com.project.devcat.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<PostDto.Response> createPost(@RequestPart(value = "data") PostDto.Request request,
                                                       @RequestPart(value = "image", required = false) List<MultipartFile> multipartFileList) {

        return postService.createPost(request, multipartFileList);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto.Response>> readPost() {

        return postService.readPost();
    }

}
