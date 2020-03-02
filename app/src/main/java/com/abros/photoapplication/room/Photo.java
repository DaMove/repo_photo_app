package com.abros.photoapplication.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "photos")
public class Photo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int entryId;

    private int id;

    private String author;

    @SerializedName("download_url")
    private String imgUrl;

    private int width;

    private int height;

    public Photo(String author, String imgUrl, int width, int height) {
        this.author = author;
        this.imgUrl = imgUrl;
        this.width = width;
        this.height = height;
    }

    public Photo(String author, String imgUrl, int square) {
        this.author = author;
        this.imgUrl = imgUrl;
        this.width = square;
        this.height = square;
    }

    public Photo() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }
}
