package com.trivia.trivia.util;


import java.io.Serializable;
import java.sql.Timestamp;

/**
 * A News Article content and it's details
 */
public class NewsArticle implements Serializable {
    private int id;
    private  String author;
    private  String title;
    private  String description;
    private  String url;
    private  Timestamp publishedAt;
    private  String urlToImage;
    private  String content;
    private String category;
    private Timestamp saveDate = new Timestamp(System.currentTimeMillis());
    public NewsArticle() {
        this.setAuthor(null);
        this.setTitle(null);
        this.setDescription(null);
        this.setUrl(null);
        this.setPublishedAt(null);
        this.setUrlToImage(null);
        this.setContent(null);
    }
    public NewsArticle(String author, String title, String description, String url, Timestamp publishedAt, String urlToImage, String content) {
        this.setAuthor(author);
        this.setTitle(title);
        this.setDescription(description);
        this.setUrl(url);
        this.setPublishedAt(publishedAt);
        this.setUrlToImage(urlToImage);
        this.setContent(content);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Timestamp getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Timestamp publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Timestamp getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Timestamp saveDate) {
        this.saveDate = saveDate;
    }
}
