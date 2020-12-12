package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;


@SuppressWarnings("PMD")
@Entity
@Table(name = "projects")
@ToString(exclude = {"userid"})
public class Project {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projectid")
    private long id;

    @Getter
    @Setter
    @Column(name = "access_level")
    private String access_level;

    @Getter
    @Setter
    @Column(name = "title")
    private String name;

    @Getter
    @Setter
    @Lob
    @Column(name = "description")
    private String description;

    @JsonIgnore
    @Getter
    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "userid")
    private User userid;

    @Getter
    @Setter
    @OneToMany(mappedBy = "projectid", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<Picture> pictures;

    public Project() {}

    public Project(String title, String access_level, String description){
        this.name = title;
        this.access_level = access_level;
        this.description = description;
    }
}
