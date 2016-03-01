package com.example.kylemcnee.apiandgsonlab;

import android.provider.MediaStore;

/**
 * Created by Kyle McNee on 3/1/2016.
 */
public class MarvelThumbnail {
    private String path;
    private String extension;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
