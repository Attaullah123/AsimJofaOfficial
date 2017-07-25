package com.cresset.asimjofaofficial.models;

/**
 * Created by attaullah.khizar on 3/7/2017.
 */

public class SliderModel {
    private String path;
    private String name;

    public SliderModel(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

