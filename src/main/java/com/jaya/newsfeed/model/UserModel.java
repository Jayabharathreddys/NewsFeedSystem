package com.jaya.newsfeed.model;

import com.jaya.newsfeed.exception.InvalidUserCreationException;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
    private String username;
    private String password;
    private List<PostModel> posts;
    private List<UserModel> following;

    public UserModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPosts(List<PostModel> posts) {
        this.posts = posts;
    }

    public void setFollowing(List<UserModel> following) {
        this.following = following;
    }

    public String getPassword() {
        return password;
    }

    public List<PostModel> getPosts() {
        return posts;
    }

    public List<UserModel> getFollowing() {
        return following;
    }

    public void addPost(PostModel post) {
        posts.add(post);
    }

    public void follow(UserModel user) {
        following.add(user);
    }

    public void unfollow(UserModel user) {
        following.remove(user);
    }

    public static Builder getBuilder(){
        return new Builder();
    }
    public static class Builder{
        private String userNm;
        private String passCd;

        public Builder setUserNm(String userNm) {
            this.userNm = userNm;
            return this;
        }

        public Builder setPassCd(String passCd) {
            this.passCd = passCd;
            return this;
        }

        private boolean isValid() throws InvalidUserCreationException{
            if (this.userNm.length() < 3)
                throw new InvalidUserCreationException("UserName must be greater than 3 length.");

            if (this.passCd.length() < 3)
                throw new InvalidUserCreationException("Password must be greater than 3 length.");

            if (this.userNm.equalsIgnoreCase(this.passCd))
                throw new InvalidUserCreationException("UserName and Password must NOT be equal.");

            return true;
        }

        public UserModel build(){
            try{
                isValid();
            }catch (Exception e){
                e.printStackTrace();
                throw new InvalidUserCreationException("User could not be created!");
            }

            UserModel userModel = new UserModel();
            userModel.setUsername(this.userNm);
            userModel.setPassword(this.passCd);
            userModel.setPosts( new ArrayList<>() );
            userModel.setFollowing( new ArrayList<>() );
            return userModel;
        }

    }
}

