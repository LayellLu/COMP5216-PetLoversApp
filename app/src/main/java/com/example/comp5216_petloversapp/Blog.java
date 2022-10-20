package com.example.comp5216_petloversapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Blog implements Parcelable {

    private String blogDescription;
    private String blogId;
    private String blogTitle;
    private String location;
    private String image;
    private String time;
    private String userEmail;
    private String key;
    private List<String> fav;

    public Blog() {
    }

    public Blog(String blogDescription, String blogId, String blogTitle, String location, String image, String time, String userEmail) {
        this.blogDescription = blogDescription;
        this.blogId = blogId;
        this.blogTitle = blogTitle;
        this.location = location;
        this.image = image;
        this.time = time;
        this.userEmail = userEmail;
    }



    protected Blog(Parcel in) {
        blogDescription = in.readString();
        blogId = in.readString();
        blogTitle = in.readString();
        location = in.readString();
        image = in.readString();
        time = in.readString();
        userEmail = in.readString();
        key = in.readString();
        fav = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(blogDescription);
        dest.writeString(blogId);
        dest.writeString(blogTitle);
        dest.writeString(location);
        dest.writeString(image);
        dest.writeString(time);
        dest.writeString(userEmail);
        dest.writeString(key);
        dest.writeStringList(fav);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Blog> CREATOR = new Creator<Blog>() {
        @Override
        public Blog createFromParcel(Parcel in) {
            return new Blog(in);
        }

        @Override
        public Blog[] newArray(int size) {
            return new Blog[size];
        }
    };

    public String getBlogDescription() {
        return blogDescription;
    }

    public void setBlogDescription(String blogDescription) {
        this.blogDescription = blogDescription;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getFav() {
        return fav;
    }

    public void setFav(List<String> fav) {
        this.fav = fav;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
