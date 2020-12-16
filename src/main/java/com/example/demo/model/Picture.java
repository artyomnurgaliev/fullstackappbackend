package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@SuppressWarnings("PMD")
@Entity
@Audited
@Table(name = "pictures")
public class Picture {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pictureid")
    private long id;

    @JsonIgnore
    @Getter
    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "projectid")
    private Project projectid;

    @Getter
    @Setter
    @Lob
    @Column(name = "src")
    private String src;

    public Picture() {}

    public Picture(String src){
        this.src = src;
    }
}
