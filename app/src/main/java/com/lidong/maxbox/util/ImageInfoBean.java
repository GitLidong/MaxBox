package com.lidong.maxbox.util;

import java.io.Serializable;

/**
 * Created by ubuntu on 17-9-15.
 */

public class ImageInfoBean implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    private String name;
    private String description;
    private String uri;

}
