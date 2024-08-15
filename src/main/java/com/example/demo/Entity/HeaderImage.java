package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "header_images")
public class HeaderImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String subtitle;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] bgImage;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
