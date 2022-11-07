package com.project.devcat.domain;

import com.project.devcat.dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Post extends Timestamped {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String category;

    private int views;

    private String filename;

    private String filepath;
//    private int commentCount;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Comment> commentList;
//
//    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<PostImage> imageList;

    public void update(PostDto.Request request) {
        this.title = request.getTitle();
        this.category = request.getCategory();
        this.content = request.getContent();
        this.filename = request.getFilename();
        this.filepath = request.getFilepath();
    }

}
