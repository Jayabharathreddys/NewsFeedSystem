package com.jaya.newsfeed.controller;

import com.jaya.newsfeed.factory.Director;
import com.jaya.newsfeed.factory.Factory;
import com.jaya.newsfeed.factory.SupportedServices;
import com.jaya.newsfeed.model.PostModel;
import com.jaya.newsfeed.model.ReplyModel;
import com.jaya.newsfeed.model.UserModel;
import com.jaya.newsfeed.utility.Utility;

import java.util.*;

public class NewsFeedController {
    public static Factory userFactory;
    public static Factory postFactory;

    public NewsFeedController() {
        Director dir = new Director(SupportedServices.USER);
        userFactory = dir.createFactory();
        Director abc = new Director(SupportedServices.POST);
        postFactory = abc.createFactory();
    }

    private static boolean commonChecks(String un){
        if (!userFactory.isUserExists(un)) {
            System.out.println("Username does not exist");
            return false;
        }
        if (userFactory.getCurrentLoggedInUser().getUsername().equalsIgnoreCase(un)) {
            System.out.println("You can not follow/unfollow yourself!");
            return false;
        }
        return true;
    }

    public void follow(String un){
        if (!commonChecks(un)) return;
        userFactory.updateFollows(un);
    }

    public void unFollow(String un){
        if (!commonChecks(un)) return;
        userFactory.updateUnFollow(un);
    }

    public static void showNewsFeed() {
        List<PostModel> newsFeed = new ArrayList<>();

        for (String follow : userFactory.getUserFollows()) {
            newsFeed.addAll(userFactory.getUserFollows(follow));
        }

        if (newsFeed.size() ==0) {
            System.out.println("No posts created until now.");
            return;
        }
        Collections.sort(newsFeed, new Comparator<PostModel>() {
            @Override
            public int compare(PostModel p1, PostModel p2) {
                return postFactory.globalUpVotes("P"+p2.getId()) - postFactory.globalUpVotes("P"+p1.getId());
            }
        });

        for (int i = 0; i < newsFeed.size(); i++) {
            PostModel post = newsFeed.get(i);
            System.out.println("Post#"+(i + 1)  +" - FeedID#"+post.getId() + ". " + post.getContent() + " - " + post.getAuthor().getUsername() + " ("
                    + postFactory.globalUpVotes("P"+post.getId()) + " upvotes)" +" - "+ Utility.numberOfMins(post.getTimestamp()));
            List<ReplyModel> replies = post.getReplies();
            for (int j = 0; j < replies.size(); j++) {
                ReplyModel reply = replies.get(j);
                System.out.println("\t"+"(Reply#"+(j + 1)  +" - FeedID#"+ reply.getId()+ "." + " " + reply.getContent()
                        + " - " + reply.getAuthor().getUsername() +" - "+ " ("
                        + postFactory.globalUpVotes("R"+reply.getId()) + " upvotes)"
                        +Utility.numberOfMins(reply.getTimestamp()) );
                for (int k = 0; k < reply.getComments().size(); k++) {
                    ReplyModel comment = reply.getComments().get(k);
                    System.out.println("\t\t"+"(Comment#"+(k + 1)+" - FeedID#"  + comment.getId()+ "." + " " + comment.getContent()
                            + " - " + comment.getAuthor().getUsername() + " ("
                            + postFactory.globalUpVotes("C"+comment.getId()) + " upvotes)"
                            +" - "+Utility.numberOfMins(comment.getTimestamp()) );
                }
            }
        }
    }

    public void downVote(char c, Integer id){

        if (c == 'C' || c == 'R') {
            if(!isThePostAssociated2(id)) return;
        }else{
            if(!isThePostAssociated(id)) return;
        }


        String tmpStr = String.valueOf(id);
        String st1= String.valueOf(c);
        st1=st1.concat(tmpStr);

        postFactory.downVote(st1);

        System.out.println("Down vote successful!");
        showNewsFeed();

    }

    public void upVote( char c, Integer id){
        if (c == 'C' || c == 'R') {
            if(!isThePostAssociated2(id)) return;
        }else{
            if(!isThePostAssociated(id)) return;
        }

        String tmpStr = String.valueOf(id);
        String st1= String.valueOf(c);
        st1=st1.concat(tmpStr);

        postFactory.upVote(st1);

        System.out.println("Upvote successful!");
        showNewsFeed();

    }

    public void addReplyContent(Integer postId, String replyContent){
        PostModel pm = globalPosts().get(postId - 1);
        ReplyModel reply = new ReplyModel(getCurrentLoggedInUser(), replyContent);
        reply.setPostId(postId);
        reply.setReplyRcomment("reply");
        reply.setComments(new ArrayList<>());
        pm.addReply(reply);
        postFactory.addToGlobalComments(reply);
    }

    public void addCommentToReplyContent(Integer replyId, String replyContent){
        ReplyModel rm = globalComments().get(replyId - 1);
        ReplyModel comment = new ReplyModel(getCurrentLoggedInUser(), replyContent);
        comment.setReplyRcomment("comment");
        comment.setPostId(rm.getPostId());
        rm.addComments(comment);
        postFactory.addToGlobalComments(comment);

        //PostModel pm = globalPosts().get(rm.getPostId() - 1);
        //ReplyModel existingRM = pm.getReplies().get(rm.getId()-1);
        //existingRM.addComments(comment);
        //pm.addReply(rm);
    }

    public boolean isThePostAssociated(Integer postId){
        if(globalPosts().size()==0) {
            System.out.println("There are no posts in your list.");
            return false;
        }

        if (postId < 1 || postId > globalPosts().size()) {
            System.out.println("Invalid post ID");
            return false;
        }

        PostModel post = globalPosts().get(postId - 1);
        String postActualOwner = post.getAuthor().getUsername();
        boolean relation = false;
        for (String follow : getUserFollows()) {
            if(follow.equalsIgnoreCase(postActualOwner))
                relation = true;
        }
        if(!relation) {
            System.out.println("The mentioned post#" + postId + " is not your list.");
            return false;
        }

        return true;
    }

    public boolean isThePostAssociated2(Integer postId){
        if(globalComments().size()==0) {
            System.out.println("There are no comments in your list.");
            return false;
        }

        if (postId < 1 || postId > globalComments().size()) {
            System.out.println("Invalid comment Feed ID");
            return false;
        }

        ReplyModel rm = globalComments().get(postId-1);
        int actualPostId = rm.getPostId();

        PostModel post = globalPosts().get(actualPostId - 1);
        String postActualOwner = post.getAuthor().getUsername();
        boolean relation = false;
        for (String follow : getUserFollows()) {
            if(follow.equalsIgnoreCase(postActualOwner))
                relation = true;
        }
        if(!relation) {
            System.out.println("The mentioned post#" + postId + " is not your list.");
            return false;
        }

        return true;
    }

    public void createPost(String content){
        PostModel pm = postFactory.createPost(userFactory.getCurrentLoggedInUser(),content);
        userFactory.globalUserPosts(pm);
        userFactory.updateUserFollowing(userFactory.getCurrentLoggedInUser().getUsername());
        postFactory.globalUpVotes("P"+pm.getId());

        System.out.println("Post successful! "+pm.toString());
        showNewsFeed();
    }

    public boolean isCredentialsValid(String nm, String pas){
            return userFactory.isEnteredCredValid(nm,pas);
    }


    public boolean isUserExists(String username){
        return userFactory.isUserExists(username);
    }

    public UserModel createUser(String userNm, String passCd){
        try{
            UserModel ud = userFactory.createUser(userNm,passCd);
            userFactory.updateUserFollowing(userNm);
            return ud;
        }catch (Exception e){
            System.out.println("Caught an exception while creating user.");
            e.printStackTrace();
            return null;
        }
    }

    public List<PostModel> globalPosts(){
        return postFactory.globalPosts();
    }

    public List<ReplyModel> globalComments(){
        return postFactory.globalComments();
    }

    public Set<String> getUserFollows(){
        return  userFactory.getUserFollows();
    }

    public UserModel getCurrentLoggedInUser(){
        return userFactory.getCurrentLoggedInUser();
    }

}
