package com.project.devcat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Shop {

    @Id
    @Column(name = "shop_id")
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String price;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private String imageUrl;

}
