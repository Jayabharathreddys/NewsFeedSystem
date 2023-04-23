package com.jaya.newsfeed.service;

import com.jaya.newsfeed.factory.Factory;
import com.jaya.newsfeed.model.PostModel;
import com.jaya.newsfeed.model.ReplyModel;
import com.jaya.newsfeed.model.UserModel;

import java.util.*;

public class UserService implements Factory {

    private static Map<String, UserModel> users = new HashMap<String, UserModel>();
    private static Map<String, Set<String>> userFollows = new HashMap<String, Set<String>>();

    private static Map<String, List<PostModel>> userPosts = new HashMap<String, List<PostModel>>();

    private static UserModel currUserObj = new UserModel();

    public UserService() {
    }


    public void globalUserPosts(PostModel pm){
        userPosts.get(currUserObj.getUsername()).add(pm);
    }

    public void updateUserFollowing(String un){
        Set<String> set = userFollows.get(getCurrentLoggedInUser());
        if (set == null) set = new HashSet<String>();
        set.add(un);
        userFollows.put(getCurrentLoggedInUser().getUsername(), set);
    }

    public UserModel createUser(String userNm, String passCd){
        try{
            UserModel um = UserModel.getBuilder().setUserNm(userNm).setPassCd(passCd).build();
            updateSingedUserList(um);
            userPosts.put(userNm, new ArrayList<>());
            updateCurrentLoggedInUser(um);
            return um;
        }catch (Exception e){
            System.out.println("Caught an exception.");
            e.printStackTrace();
            throw e;
        }

    }

    @Override
    public int globalUpVotes(String s) {
        return 0;
    }

    @Override
    public List<PostModel> globalPosts() {
        return null;
    }

    @Override
    public List<ReplyModel> globalComments() {
        return null;
    }

    @Override
    public void downVote(String st1) {

    }

    @Override
    public void upVote(String st1) {

    }

    @Override
    public void addToGlobalComments(ReplyModel reply) {

    }

    @Override
    public PostModel createPost(UserModel currentLoggedInUser, String content) {
        return null;
    }

    public boolean isEnteredCredValid(String username, String pass){
        currUserObj = users.get(username);
        if (!pass.equals(currUserObj.getPassword())) {
            System.out.println("Incorrect password -->"+ currUserObj.getPassword());
            return false;
        }
        return true;
    }

    public void updateUnFollow(String un){
        Set<String> follows = userFollows.get(getCurrentLoggedInUser().getUsername());

        if (!follows.contains(un)) {
            System.out.println(getCurrentLoggedInUser().getUsername()+", you are not following "+un);
            return;
        }

        follows.remove(un);

        System.out.println("Unfollowing "+un.toUpperCase()+" is successful!");
    }

    public void updateFollows(String un){
        Set<String> follows = userFollows.get(getCurrentLoggedInUser().getUsername());

        if (follows.contains(un)) {
            System.out.println(getCurrentLoggedInUser().getUsername()+" you are already following "+un);
            return;
        }

        follows.add(un);

        System.out.println("Following "+un.toUpperCase()+" is successful!");
    }
    public boolean isUserExists(String username){
        return users.containsKey(username);
    }

    public UserModel getUserObj(String nm){
        return users.get(nm);
    }
    public UserModel getCurrentLoggedInUser(){
        return currUserObj;
    }

    public void updateCurrentLoggedInUser(UserModel um){
         currUserObj = um;
    }

    public void updateSingedUserList(UserModel um){
        users.put(um.getUsername(), um);
    }

    public Set<String> getUserFollows(){
        return userFollows.get(getCurrentLoggedInUser().getUsername());
    }

    public List<PostModel> getUserFollows(String follow){
        return  userPosts.get(follow);
    }

}
