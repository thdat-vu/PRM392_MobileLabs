package com.example.lab5;

public class Item {
    private String title;
    private String content;

    public Item(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Getter và Setter
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }


}
