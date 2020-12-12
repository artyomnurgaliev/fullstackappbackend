package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@ToString
@SuppressWarnings("PMD")
@Entity
@Table(name = "users")
public class User {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private long userid;

    @Getter
    @Setter
    @Column(name = "login")
    private String login;

    @Getter
    @Setter
    @Column(name = "password")
    private String password;

    @Getter
    @Setter
    @Column(name = "fullname")
    private String fullname;

    @Getter
    @Setter
    @Lob
    @Column(name = "description")
    private String description;

    @Getter
    @Setter
    @Lob
    @Column(name = "photo")
    private String photo;

    @Getter
    @Setter
    @OneToMany(mappedBy = "userid", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<Project> projects;

    public User() {
    }

    public User(String login, String password, String fullname, String description, String photo) {
        super();
        this.login = login;
        this.password = password;
        this.fullname = fullname;
        this.description = description;
        this.photo = photo;
    }
}