package com.task.imager;

public class Collection {
    public float id;
    public String title;
    public String description;
    public String published_at;
    public String last_collected_at;
    public String updated_at;
    public float total_photos;
    public boolean private1;
    public String share_key;
    Cover_photo Cover_photoObject;
    User UserObject;
    Links LinksObject;

    public class User {
        public String id;
        public String updated_at;
        public String username;
        public String name;
        public String portfolio_url;
        public String bio;
        public String location;
        public float total_likes;
        public float total_photos;
        public float total_collections;
        Profile_image Profile_imageObject;
        Links LinksObject;
    }

    public class Links {
        public String self;
        public String html;
        public String photos;
        public String likes;
        public String portfolio;
        public String download;
    }

    public class Profile_image {
        public String small;
        public String medium;
        public String large;
    }

    public class Cover_photo {
        public String id;
        public float width;
        public float height;
        public String color;
        public String blur_hash;
        public float likes;
        public boolean liked_by_user;
        public String description;
        User UserObject;
        Urls UrlsObject;
        Links LinksObject;
    }

    public class Urls {
        public String raw;
        public String full;
        public String regular;
        public String small;
        public String thumb;
    }
}