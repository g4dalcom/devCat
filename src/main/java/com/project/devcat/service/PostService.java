package com.project.devcat.service;

import com.project.devcat.domain.Member;
import com.project.devcat.domain.MemberRoleEnum;
import com.project.devcat.domain.Post;
import com.project.devcat.dto.PostDto;
import com.project.devcat.repository.MemberRepository;
import com.project.devcat.repository.PostImageRepository;
import com.project.devcat.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final MemberRepository memberRepository;

    /**
     * 게시글 작성하기
     */
    @Transactional
    public ResponseEntity<PostDto.Response> createPost(PostDto.Request request, List<MultipartFile> multipartFileList) {
        Post post = request.toEntity();
        postRepository.save(post);
        return ResponseEntity.ok().body(PostDto.Response.builder()
                .id(post.getId())
//                .member_id(post.getMember().getId())
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory())
                .views(post.getViews())
                .commentCount(post.getCommentCount())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build());
    }

    @Transactional
    public ResponseEntity<List<PostDto.Response>> readPost() {
        List<Post> postList = postRepository.findAll();

        List<PostDto.Response> postData = new ArrayList<>();

        for (Post posts : postList) {
            PostDto.Response response = new PostDto.Response(posts);
            postData.add(response);
        }
        return ResponseEntity.ok().body(postData);
    }

    /**
     * 테스트용
     */
    @PostConstruct
    public void init() {
        memberRepository.save(new Member(1L, "멤버1", "닉네임", "1234", MemberRoleEnum.MEMBER));
    }
}