package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@SuppressWarnings("PMD")
@Entity
@Table(name = "pictures")
public class Picture {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    private long id;

    @JsonIgnore
    @Getter
    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "PROJECT_ID")
    private Project PROJECT_ID;

    @Getter
    @Setter
    @Column(name = "image")
    byte[] image;

    public Picture() {}

    public Picture(byte[] image){
        this.image = image;
    }
}
