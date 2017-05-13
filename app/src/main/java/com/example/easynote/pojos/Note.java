package com.example.easynote.pojos;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by SEVAK on 09.05.2017.
 */

public class Note implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String title;
    private String description;
    private Date createDate;
    private int color;
    private boolean important;

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public int getColor() {
        return color;
    }

    public boolean isImportant() {
        return important;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createDate=" + createDate +
                ", color=" + color +
                ", important=" + important +
                '}';
    }
}
