package com.task.imager.API;

public class Collection {
    private float id;
    private String title;
    private String description;
    private String published_at;
    private String last_collected_at;
    private String updated_at;
    private float total_photos;
    private boolean private1;
    private String share_key;
    private Cover_photo Cover_photoObject;
    private User UserObject;
    private Links LinksObject;

    public class User {
        private String id;
        private String updated_at;
        private String username;
        private String name;
        private String portfolio_url;
        private String bio;
        private String location;
        private float total_likes;
        private float total_photos;
        private float total_collections;
        private Profile_image Profile_imageObject;
        private Links LinksObject;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPortfolio_url() {
            return portfolio_url;
        }

        public void setPortfolio_url(String portfolio_url) {
            this.portfolio_url = portfolio_url;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public float getTotal_likes() {
            return total_likes;
        }

        public void setTotal_likes(float total_likes) {
            this.total_likes = total_likes;
        }

        public float getTotal_photos() {
            return total_photos;
        }

        public void setTotal_photos(float total_photos) {
            this.total_photos = total_photos;
        }

        public float getTotal_collections() {
            return total_collections;
        }

        public void setTotal_collections(float total_collections) {
            this.total_collections = total_collections;
        }

        public Profile_image getProfile_imageObject() {
            return Profile_imageObject;
        }

        public void setProfile_imageObject(Profile_image profile_imageObject) {
            Profile_imageObject = profile_imageObject;
        }

        public Links getLinksObject() {
            return LinksObject;
        }

        public void setLinksObject(Links linksObject) {
            LinksObject = linksObject;
        }
    }

    public class Links {
        private String self;
        private String html;
        private String photos;
        private String likes;
        private String portfolio;
        private String download;

        public String getSelf() {
            return self;
        }

        public void setSelf(String self) {
            this.self = self;
        }

        public String getHtml() {
            return html;
        }

        public void setHtml(String html) {
            this.html = html;
        }

        public String getPhotos() {
            return photos;
        }

        public void setPhotos(String photos) {
            this.photos = photos;
        }

        public String getLikes() {
            return likes;
        }

        public void setLikes(String likes) {
            this.likes = likes;
        }

        public String getPortfolio() {
            return portfolio;
        }

        public void setPortfolio(String portfolio) {
            this.portfolio = portfolio;
        }

        public String getDownload() {
            return download;
        }

        public void setDownload(String download) {
            this.download = download;
        }
    }

    public class Profile_image {
        private String small;
        private String medium;
        private String large;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }
    }

    public class Cover_photo {
        private String id;
        private float width;
        private float height;
        private String color;
        private String blur_hash;
        private float likes;
        private boolean liked_by_user;
        private String description;
        private User UserObject;
        private Urls UrlsObject;
        private Links LinksObject;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public float getWidth() {
            return width;
        }

        public void setWidth(float width) {
            this.width = width;
        }

        public float getHeight() {
            return height;
        }

        public void setHeight(float height) {
            this.height = height;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getBlur_hash() {
            return blur_hash;
        }

        public void setBlur_hash(String blur_hash) {
            this.blur_hash = blur_hash;
        }

        public float getLikes() {
            return likes;
        }

        public void setLikes(float likes) {
            this.likes = likes;
        }

        public boolean isLiked_by_user() {
            return liked_by_user;
        }

        public void setLiked_by_user(boolean liked_by_user) {
            this.liked_by_user = liked_by_user;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public User getUserObject() {
            return UserObject;
        }

        public void setUserObject(User userObject) {
            UserObject = userObject;
        }

        public Urls getUrlsObject() {
            return UrlsObject;
        }

        public void setUrlsObject(Urls urlsObject) {
            UrlsObject = urlsObject;
        }

        public Links getLinksObject() {
            return LinksObject;
        }

        public void setLinksObject(Links linksObject) {
            LinksObject = linksObject;
        }
    }

    public class Urls {
        private String raw;
        private String full;
        private String regular;
        private String small;
        private String thumb;

        public String getRaw() {
            return raw;
        }

        public void setRaw(String raw) {
            this.raw = raw;
        }

        public String getFull() {
            return full;
        }

        public void setFull(String full) {
            this.full = full;
        }

        public String getRegular() {
            return regular;
        }

        public void setRegular(String regular) {
            this.regular = regular;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
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

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public String getLast_collected_at() {
        return last_collected_at;
    }

    public void setLast_collected_at(String last_collected_at) {
        this.last_collected_at = last_collected_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public float getTotal_photos() {
        return total_photos;
    }

    public void setTotal_photos(float total_photos) {
        this.total_photos = total_photos;
    }

    public boolean isPrivate1() {
        return private1;
    }

    public void setPrivate1(boolean private1) {
        this.private1 = private1;
    }

    public String getShare_key() {
        return share_key;
    }

    public void setShare_key(String share_key) {
        this.share_key = share_key;
    }

    public Cover_photo getCover_photoObject() {
        return Cover_photoObject;
    }

    public void setCover_photoObject(Cover_photo cover_photoObject) {
        Cover_photoObject = cover_photoObject;
    }

    public User getUserObject() {
        return UserObject;
    }

    public void setUserObject(User userObject) {
        UserObject = userObject;
    }

    public Links getLinksObject() {
        return LinksObject;
    }

    public void setLinksObject(Links linksObject) {
        LinksObject = linksObject;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", published_at='" + published_at + '\'' +
                ", last_collected_at='" + last_collected_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", total_photos=" + total_photos +
                ", private1=" + private1 +
                ", share_key='" + share_key + '\'' +
                ", Cover_photoObject=" + Cover_photoObject +
                ", UserObject=" + UserObject +
                ", LinksObject=" + LinksObject +
                '}';
    }
}