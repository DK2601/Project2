package com.project.spotify.entity;

public class Category {
    private String name;
    private String iconUrl;

    public Category(String name, String iconUrl) {
        this.name = name;
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}
