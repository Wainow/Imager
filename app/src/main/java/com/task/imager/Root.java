package com.task.imager;

import java.util.Date;
import java.util.List;

public class Root {
    public String id;
    public Date created_at;
    public Date updated_at;
    public Date promoted_at;
    public int width;
    public int height;
    public String color;
    public String blur_hash;
    public String description;
    public String alt_description;
    public Urls urls;
    public Links links;
    public List<Object> categories;
    public int likes;
    public boolean liked_by_user;
    public List<Object> current_user_collections;
    public Object sponsorship;
    public User user;
    public Exif exif;
    public Location location;
    public int views;
    public int downloads;

    public class Urls{
        public String raw;
        public String full;
        public String regular;
        public String small;
        public String thumb;
    }

    public class Links{

        public String self;
        public String html;
        public String download;
        public String download_location;
    }

    public class Links2{
        public String self;
        public String html;
        public String photos;
        public String likes;
        public String portfolio;
        public String following;
        public String followers;
    }

    public class ProfileImage{
        public String small;
        public String medium;
        public String large;
    }

    public class User{
        public String id;
        public Date updated_at;
        public String username;
        public String name;
        public String first_name;
        public String last_name;
        public String twitter_username;
        public Object portfolio_url;
        public String bio;
        public String location;
        public Links2 links;
        public ProfileImage profile_image;
        public String instagram_username;
        public int total_collections;
        public int total_likes;
        public int total_photos;
        public boolean accepted_tos;
    }

    public class Exif{
        public String make;
        public String model;
        public String exposure_time;
        public String aperture;
        public String focal_length;
        public int iso;
    }

    public class Position{
        public Object latitude;
        public Object longitude;
    }

    public class Location{
        public Object title;
        public Object name;
        public Object city;
        public Object country;
        public Position position;
    }
}
