package com.surmize.tweetanalysis;

import com.surmize.models.TwitterUser;

public class TweetAlgorithms {

    
    
    public double calculateInfluence(TwitterUser user){
        //(followers_count * (followers_count/statuses_count))
        return (user.followersCount * (user.followersCount/user.statusesCount));
    }
}
