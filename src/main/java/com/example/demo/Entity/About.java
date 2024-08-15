package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "about")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class About {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private String title;
//    private String subtitle;
    private String description;
    private int yearsOfExperience;
    private int numberOfChefs;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image1;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image2;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image3;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image4;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
