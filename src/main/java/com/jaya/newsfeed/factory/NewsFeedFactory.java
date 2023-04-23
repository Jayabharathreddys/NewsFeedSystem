package com.jaya.newsfeed.factory;

import com.jaya.newsfeed.service.PostService;
import com.jaya.newsfeed.service.UserService;

public class NewsFeedFactory {
    public static Factory createServiceFactory(SupportedServices platform){
        return switch (platform){
                case POST -> new PostService();
                case USER -> new UserService();
        };
    }
}
