package com.jaya.newsfeed.factory;

import com.jaya.newsfeed.model.PostModel;
import com.jaya.newsfeed.model.ReplyModel;
import com.jaya.newsfeed.model.UserModel;

import java.util.List;
import java.util.Set;

public interface Factory {
    boolean isUserExists(String un);

    UserModel getCurrentLoggedInUser();

    void updateFollows(String un);

    void updateUnFollow(String un);


    public Set<String> getUserFollows();

    public List<PostModel> getUserFollows(String follow);

    boolean isEnteredCredValid(String nm, String pas);

    void globalUserPosts(PostModel pm);

    void updateUserFollowing(String username);

    UserModel createUser(String userNm, String passCd);

    int globalUpVotes(String s);

    List<PostModel> globalPosts();

    List<ReplyModel> globalComments();

    void downVote(String st1);

    void upVote(String st1);

    void addToGlobalComments(ReplyModel reply);

    PostModel createPost(UserModel currentLoggedInUser, String content);
}
