package com.example.comp5216_petloversapp;

import java.io.Serializable;

public class CommentItem implements Serializable {

    public String userId;
    public String content;
    public String blogId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }
}
