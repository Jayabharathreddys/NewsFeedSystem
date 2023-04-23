package com.jaya.newsfeed.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReplyModel {
    private static int nextId = 1;

    private final int id;

    private UUID uuid = UUID.randomUUID();
    private final UserModel author;
    private final String content;
    private final LocalDateTime timestamp;

    private int postId;

    private String replyRcomment;

    private List<ReplyModel> comments;


    public ReplyModel(UserModel author, String content) {
        this.id = nextId++;
        this.author = author;
        this.content = content;
        this.timestamp = LocalDateTime.now();
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


    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public List<ReplyModel> getComments() {
        return comments;
    }

    public void setComments(List<ReplyModel> comments) {
        this.comments = comments;
    }

    public void addComments(ReplyModel reply) {
        if (comments==null)
            comments=new ArrayList<ReplyModel>();

        comments.add(reply);
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getReplyRcomment() {
        return replyRcomment;
    }

    public void setReplyRcomment(String replyRcomment) {
        this.replyRcomment = replyRcomment;
    }

    @Override
    public String toString() {
        return "ReplyModel{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", author=" + author.getUsername() +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", postId=" + postId +
                ", replyRcomment='" + replyRcomment + '\'' +
                ", comments=" + comments +
                '}';
    }
}
