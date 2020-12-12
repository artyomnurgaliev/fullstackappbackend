package com.example.demo.representation;

import lombok.Getter;
import lombok.Setter;

public class PictureRepresentation {
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String src;

    PictureRepresentation() { }
    PictureRepresentation(String id, String src) {
        this.id = id;
        this.src = src;
    }
}
