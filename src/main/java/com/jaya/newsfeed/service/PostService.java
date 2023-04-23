package com.jaya.newsfeed.service;

import com.jaya.newsfeed.factory.Factory;
import com.jaya.newsfeed.model.PostModel;
import com.jaya.newsfeed.model.ReplyModel;
import com.jaya.newsfeed.model.UserModel;

import java.util.*;

public class PostService implements Factory {

    private static List<PostModel> allPosts = new ArrayList<>();
    private static List<ReplyModel> allComments = new ArrayList<>();
    private static Map<String, Integer> postUpvotes = new HashMap<String, Integer>();

    public PostModel createPost(UserModel um, String content){
        PostModel pm =  PostModel.getBuilder().setAuth(um).setCont(content).build();
        allPosts.add(pm);
        return pm;
    }

    public static boolean containsPostInUpVotes(String postId){
        if (!postUpvotes.containsKey(postId))
            return false;

        return true;
    }

    public void upVote(String postId){
        //Integer id = Integer.parseInt(postId.substring(1));
        int val = 0;
        if ( containsPostInUpVotes(postId) )
            val = postUpvotes.get(postId);
        else
            val= 0;

        postUpvotes.put(postId,val+1);
    }

    public void downVote(String postId){
        int val = 0;
        if ( containsPostInUpVotes(postId) )
            val = postUpvotes.get(postId);
        else
            val= 0;

        postUpvotes.put(postId,val-1);
    }

    public int globalUpVotes(String postID){
        if ( containsPostInUpVotes(postID) )
            return postUpvotes.get(postID);
        else
            return 0;
    }

    public List<PostModel> globalPosts(){
        return allPosts;
    }

    public List<ReplyModel> globalComments(){
        return allComments;
    }

    public void addToGlobalComments(ReplyModel rm){
        allComments.add(rm);
    }

    @Override
    public boolean isUserExists(String un) {
        return false;
    }

    @Override
    public UserModel getCurrentLoggedInUser() {
        return null;
    }

    @Override
    public void updateFollows(String un) {

    }

    @Override
    public void updateUnFollow(String un) {

    }


    public Set<String> getUserFollows(){
        return null;
    }

    public List<PostModel> getUserFollows(String follow){
        return  null;
    }

    @Override
    public boolean isEnteredCredValid(String nm, String pas) {
        return false;
    }

    @Override
    public void globalUserPosts(PostModel pm) {

    }

    @Override
    public void updateUserFollowing(String username) {

    }

    @Override
    public UserModel createUser(String userNm, String passCd) {
        return null;
    }

}
