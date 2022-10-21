package com.project.devcat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Place {

    @Id
    @Column(name = "place_id")
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String areaCode;

    @Column(nullable = false)
    private String singunguCode;

    @Column(nullable = false)
    private String mapx;

    @Column(nullable = false)
    private String mapy;

    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PlaceImage> imageList;

}
