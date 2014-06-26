package com.surmize.tweetanalysis;

import com.surmize.dao.TweetDAO;
import com.surmize.models.Tweet;
import com.surmize.models.TwitterUser;
import com.surmize.textalytics.AnalyzedDocument;
import com.surmize.textalytics.TextAnalyzer;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TweetAnalyzer {

    private final TweetDAO tweetDao;
    private final TextAnalyzer textAnalzer;
    
    public TweetAnalyzer() {
        tweetDao = new TweetDAO();
        textAnalzer = new TextAnalyzer();
    }
    
    public void analyzeTweet(Long tweetId){
        try {
            Tweet t = tweetDao.getTweetWithUser(tweetId);
            System.out.println(t.text);
            identifyStockSymbols(t);
            determineSentiment(t);
            hasLink(t);
            calculateInfluence(t.user);
        } catch (SQLException ex) {
            Logger.getLogger(TweetAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void identifyStockSymbols(Tweet t){
        // regex to find symbols.  Insert into DB
    } 
    
    public void determineSentiment(Tweet t){
        AnalyzedDocument document = textAnalzer.analyze(t.text);
        System.out.println("sentiment: "+document.getSentiment());
    }
    
    public void hasLink(Tweet t){
        boolean haslink = t.text.toLowerCase().contains("http:");
        System.out.println("Has Link: "+haslink);
    }
    
    public double calculateInfluence(TwitterUser user){
        //(followers_count * (followers_count/statuses_count))
        if(user == null){
            return 0d;
        }
        
        double influence = ((double)user.followersCount * ((double)user.followersCount /(double)user.statusesCount ));
        System.out.println("Influence: "+influence);
        return influence;
    }
}
