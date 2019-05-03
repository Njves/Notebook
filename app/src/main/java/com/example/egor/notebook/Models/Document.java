package com.example.egor.notebook.Models;

import android.support.annotation.NonNull;

import java.io.File;
import java.net.URI;
import java.util.Date;

public class Document extends File {
    private String title;
    private Date date;
    private static String extension;






    public Document(@NonNull URI uri) {
        super(uri);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
