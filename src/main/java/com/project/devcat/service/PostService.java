package com.project.devcat.service;

import com.project.devcat.domain.Member;
import com.project.devcat.domain.MemberRoleEnum;
import com.project.devcat.domain.Post;
import com.project.devcat.dto.PostDto;
import com.project.devcat.repository.MemberRepository;
import com.project.devcat.repository.PostImageRepository;
import com.project.devcat.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final MemberRepository memberRepository;


    /* 게시글 등록 */
    public ResponseEntity<PostDto.Response> createPost(PostDto.Request request) {
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .views(request.getViews())
                .build();

        postRepository.save(post);
        return ResponseEntity.ok().body(new PostDto.Response());
    }

    /* 게시글 수정하기 */
    public PostDto.Response updatePost(Long post_id, PostDto.Request request) {
        Post post = postRepository.findById(post_id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        post.update(request);
        log.info("request={}", request);
        log.info("post={}", post);

        postRepository.save(post);
        return PostDto.Response.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory())
                .views(post.getViews())
                .build();
    }


    /**
     * 테스트용
     */
    @PostConstruct
    public void init() {
        memberRepository.save(new Member(1L, "멤버1", "닉네임", "1234", MemberRoleEnum.MEMBER));
    }


}
