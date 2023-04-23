package com.jaya.newsfeed.model;

import com.jaya.newsfeed.exception.InvalidUserCreationException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PostModel {
    private static int nextId = 1;

    private int id;

    private UUID uuid = UUID.randomUUID();
    private UserModel author;
    private String content;
    private LocalDateTime timestamp;
    private List<ReplyModel> replies;
    private int upVote = -1;
    private int downVote = 1;

    public static void setNextId(int nextId) {
        PostModel.nextId = nextId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAuthor(UserModel author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setReplies(List<ReplyModel> replies) {
        this.replies = replies;
    }

    public PostModel() {

    }

    public int getId() {
        return id;
    }

    public UserModel getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public List<ReplyModel> getReplies() {
        return replies;
    }

    public int getUpVotes() {
        return upVote;
    }

    public int getDownVotes() {
        return downVote;
    }

    public void addReply(ReplyModel reply) {
        replies.add(reply);
    }

    public void upvote() {
        upVote++;
    }

    public void downVote() {
        downVote++;
    }


    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", author=" + author +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", replies=" + replies +
                ", upvote=" + upVote +
                ", down-votes=" + downVote +
                '}';
    }


    public static Builder getBuilder(){
        return new Builder();
    }
    public static class Builder{
        private UserModel auth;
        private String cont;

        public Builder setAuth(UserModel auth) {
            this.auth = auth;
            return this;
        }

        public Builder setCont(String cont) {
            this.cont = cont;
            return this;
        }

        private boolean isValid() throws InvalidUserCreationException {

            if (this.cont.length() < 2)
                throw new InvalidUserCreationException("Content must be greater than 2 length.");

            return true;
        }

        public PostModel build(){
            try{
                isValid();
            }catch (Exception e){
                throw new InvalidUserCreationException("Post could not be created!");
            }

            PostModel postModel = new PostModel();
            postModel.setId(nextId++);
            postModel.setAuthor(auth);
            postModel.setContent(cont);
            postModel.setTimestamp(LocalDateTime.now());
            postModel.setReplies(new ArrayList<>());
            postModel.upvote();
            postModel.downVote();

            return postModel;
        }

    }
}
