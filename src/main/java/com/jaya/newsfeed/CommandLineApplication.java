package com.jaya.newsfeed;

import com.jaya.newsfeed.controller.NewsFeedController;
import com.jaya.newsfeed.model.UserModel;

import java.util.Scanner;

public class CommandLineApplication {

    private static Scanner scanner = new Scanner(System.in);

    private static  NewsFeedController controller = new NewsFeedController();
    public static void main(String[] args) {

        while (true) {
            System.out.println("Welcome to the Social Network!");
            System.out.println("1. Sign up");
            System.out.println("2. Log in");
            System.out.println("3. Exit");
            String str = scanner.nextLine();
            int choice = Integer.parseInt((str!=null && !str.isEmpty() && str.trim().length()>0) ? str: "0");
            //int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    signUp();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void login() {
        System.out.println("Enter your username:");
        String username = scanner.nextLine();

        if (!controller.isUserExists(username)) {
            System.out.println("Username does not exist");
            return;
        }

        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        if (!controller.isCredentialsValid(username, password) )  {

            return;
        }
        System.out.println("Login successful");

        while (true) {
            System.out.println("Welcome, " + username + "!");
            System.out.println("1. Post");
            System.out.println("2. ReplyToPost");
            System.out.println("3. CommentToReply");
            System.out.println("4. UpVote/DownVote");
            System.out.println("5. Follow");
            System.out.println("6. Unfollow");
            System.out.println("7. Show news feed");
            System.out.println("8. Logout");
            int choice =0;
            try {
                String str = scanner.nextLine();
                choice = Integer.parseInt((str!=null && !str.isEmpty() && str.trim().length()>0) ? str: "0");
            }catch (Exception e){
                System.out.println("exception occurred"+e.getMessage());
            }
            switch (choice) {
                case 1:
                    post();
                    break;
                case 2:
                    reply();
                    break;
                case 3:
                    commentTheReply();
                    break;
                case 4:
                    vote();
                    break;
                case 5:
                    follow();
                    break;
                case 6:
                    unfollow();
                    break;
                case 7:
                    controller.showNewsFeed();
                    break;
                case 8:
                    System.out.println(username+" logged out successfully.");
                    username = null;
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }


    private static void vote() {

        while (true) {
            System.out.println("Pls, select the appropriate!");
            System.out.println("1. UpvoteThePOST");
            System.out.println("2. DownVoteThePOST");
            System.out.println("3. UpvoteTheREPLY");
            System.out.println("4. DownVoteTheREPLY");
            System.out.println("5. upvoteTheComment");
            System.out.println("6. downVoteTheComment");
            System.out.println("7. Main menu");
            int choice =0;
            try {
                String str = scanner.nextLine();
                choice = Integer.parseInt((str!=null && !str.isEmpty() && str.trim().length()>0) ? str: "0");
            }catch (Exception e){
                System.out.println("exception occurred"+e.getMessage());
            }
            switch (choice) {
                case 1:
                    upvote();
                    break;
                case 2:
                    downVote();
                    break;
                case 3:
                    upvoteReply();
                    break;
                case 4:
                    downVoteReply();
                    break;
                case 5:
                    upvoteComment();
                    break;
                case 6:
                    downVoteComment();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }


    private static void follow() {
        System.out.println("Enter the username you want to follow:");
        String username = scanner.nextLine();
        controller.follow(username);

    }


    private static void unfollow() {
        System.out.println("Enter the username you want to unfollow:");
        try {
            String username = scanner.nextLine();

            controller.unFollow(username);

        }catch (Exception e){
            System.out.println("Exception occurred!");
        }
    }

    private static void downVote() {
        System.out.println("Enter the FeedID# of the post you want to down vote:");
        try{
            int postId = Integer.parseInt(scanner.nextLine());
            controller.downVote( 'P', postId);

        }catch (Exception e){
            System.out.println("Exception occurred!");
        }
    }

    private static void upvote() {
        System.out.println("Enter the FeedID# of the post you want to upvote:");
        try{
            int postId = Integer.parseInt(scanner.nextLine());
            controller.upVote('P', postId);

        }catch (Exception e){
            System.out.println("Exception occurred!");
        }
    }

    private static void downVoteComment() {
        System.out.println("Enter the FeedID# of the COMMENT you want to down vote:");
        try{
            int postId = Integer.parseInt(scanner.nextLine());
            controller.downVote('C', postId);

        }catch (Exception e){
            System.out.println("Exception occurred!");
        }
    }

    private static void upvoteComment() {
        System.out.println("Enter the FeedID# of the COMMENT you want to upvote:");
        try{
            int commentID = Integer.parseInt(scanner.nextLine());
            controller.upVote('C', commentID);

        }catch (Exception e){
            System.out.println("Exception occurred!");
        }
    }

    private static void downVoteReply() {
        System.out.println("Enter the FeedID# of the COMMENT you want to down vote:");
        try{
            int postId = Integer.parseInt(scanner.nextLine());
            controller.downVote('R', postId);

        }catch (Exception e){
            System.out.println("Exception occurred!");
        }
    }

    private static void upvoteReply() {
        System.out.println("Enter the FeedID# of the COMMENT you want to upvote:");
        try{
            int commentID = Integer.parseInt(scanner.nextLine());
            controller.upVote('R', commentID);

        }catch (Exception e){
            System.out.println("Exception occurred!");
        }
    }
    private static void reply() {
        controller.showNewsFeed();
        System.out.println("Enter the FeedID# of the post you want to reply to:");
        try{
            int postId = Integer.parseInt(scanner.nextLine());

            if (postId < 1 || postId > controller.globalPosts().size()) {
                System.out.println("Invalid post ID");
                return;
            }
            if (!controller.isThePostAssociated(postId)) return;

            System.out.println("Enter your reply:");
            String replyContent = scanner.nextLine();

            controller.addReplyContent(postId,replyContent);

            System.out.println("Reply successful!");
            controller.showNewsFeed();
        }catch (Exception e){
            System.out.println("Exception occurred!");
        }
    }

    private static void commentTheReply() {
        NewsFeedController.showNewsFeed();
        System.out.println("Enter the FeedID# of the REPLY you want to comment to:");
        try{
            int replyFeedId = Integer.parseInt(scanner.nextLine());

            if (replyFeedId < 1 || replyFeedId > controller.globalComments().size()) {
                System.out.println("Invalid Reply Feed ID");
                return;
            }
            int postId = controller.globalComments().get(replyFeedId-1).getPostId();
            if (!controller.isThePostAssociated( postId )) return;

            System.out.println("Enter your comment:");
            String replyContent = scanner.nextLine();

            controller.addCommentToReplyContent(replyFeedId,replyContent);

            System.out.println("Comment to Reply is successful!");
            controller.showNewsFeed();
        }catch (Exception e){
            System.out.println("Exception occurred!");
        }
    }
    private static void post() {
        try {
            System.out.println("Enter your post content:");
            String content = scanner.nextLine();

            controller.createPost(content);
        }catch (Exception e){
            System.out.println("Exception occurred!");
        }
    }


    private static void signUp() {
        System.out.println("Enter a username:");
        String username = scanner.nextLine();

        if (controller.isUserExists(username)) {
            System.out.println("Username already exists");
            return;
        }

        System.out.println("Enter a password:");
        String password = scanner.nextLine();

        UserModel um = controller.createUser(username, password);
        if (um==null) {
            System.out.println("Sign up is NOT successful!");
            return;
        }

        System.out.println("Sign up successful!");
    }

}
