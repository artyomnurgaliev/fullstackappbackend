package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Collection;

@SuppressWarnings("PMD")
@Entity
@Table(name = "users")
public class User {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private long user_id;

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
    @Column(name = "description")
    private String description;


    @Getter
    @Setter
    @OneToMany(mappedBy = "USER_ID", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<Project> projects;

    public User() {
    }

    public User(String login, String password, String fullname, String description) {
        super();
        this.login = login;
        this.password = password;
        this.fullname = fullname;
        this.description = description;
    }
}