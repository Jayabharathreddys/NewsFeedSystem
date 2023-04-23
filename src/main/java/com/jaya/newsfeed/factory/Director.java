package com.jaya.newsfeed.factory;

public class Director {
    private SupportedServices platform;

    public Director(SupportedServices platform) {
        this.platform = platform;
    }

    public Factory createFactory(){
        return NewsFeedFactory.createServiceFactory(platform);
    }
}
