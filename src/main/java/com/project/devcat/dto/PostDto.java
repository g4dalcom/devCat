package com.project.devcat.dto;

import com.project.devcat.domain.Member;
import com.project.devcat.domain.Post;
import com.project.devcat.domain.PostImage;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class PostDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private String title;
        private String content;
        private String category;
//        private List<PostImage> imageList;

        public Post toEntity() {
            return Post.builder()
                    .title(title)
                    .content(content)
                    .category(category)
//                    .views(0)
//                    .commentCount(0)
//                    .imageList(imageList)
                    .build();
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class Response {
        private Long id;
        private String title;
        private String content;
        private String category;
        private List<PostImage> imageList;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private int views;
        private int commentCount;
//        private Long member_id;

        public Response(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.category = post.getCategory();
//            this.imageList = post.getImageList();
            this.createdAt = post.getCreatedAt();
            this.modifiedAt = post.getModifiedAt();
//            this.views = post..getViews();
//            this.commentCount = post.getCommentCount();
//            this.member_id = post.getMember().getId();
        }
    }

}
