package com.example.demo.representation;

import com.example.demo.model.Picture;
import com.example.demo.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

public class ProjectRepresentation {

    @Getter
    @Setter
    private String access_level;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @Lob
    private String description;

    @Getter
    @Setter
    private Collection<PictureRepresentation> pictures;

    public ProjectRepresentation() {}

    public ProjectRepresentation(String title, String access_level, String description, Collection<PictureRepresentation> pictures){
        this.name = title;
        this.access_level = access_level;
        this.description = description;
        this.pictures = pictures;
    }
}
