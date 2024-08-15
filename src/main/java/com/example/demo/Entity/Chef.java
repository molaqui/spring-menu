package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chefs")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Chef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String designation;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    private String facebookUrl;
    private String instagramUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

