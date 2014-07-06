package com.surmize.tweetanalysis;

import com.surmize.dao.StockSymbolDAO;
import com.surmize.dao.TweetAnalyticsDAO;
import com.surmize.dao.TweetDAO;
import com.surmize.dao.TweetStockDAO;
import com.surmize.models.StockSymbol;
import com.surmize.models.Tweet;
import com.surmize.models.TweetAnalytics;
import com.surmize.models.TweetStock;
import com.surmize.models.TwitterUser;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TweetAnalyzer {

    private final TweetDAO tweetDao;
    private final TweetAnalyticsDAO analyticsDao;
    private final TweetStockDAO tweetStockDao;
    private final StockSymbolDAO stockDao;
    
    private final TweetSentimentAnalyzer sentimentAnalzer;
    private Map<String, Long> stockSymbolMap = null;
    
    public TweetAnalyzer() {
        tweetDao = new TweetDAO();
        analyticsDao = new TweetAnalyticsDAO();
        tweetStockDao = new TweetStockDAO();
        stockDao = new StockSymbolDAO();
        sentimentAnalzer = new TweetSentimentAnalyzer();
    }
    
    public void analyzeTweet(Long tweetId){
        try {
            Tweet t = tweetDao.getTweetWithUser(tweetId);
            
            TweetAnalytics analytics = new TweetAnalytics();
            analytics.tweetId = t.id;
            analytics.sentiment = determineSentiment(t);
            analytics.hasLink = hasLink(t);
            analytics.influence = calculateInfluence(t.user);
            analyticsDao.insertEntity(analytics);
            
            identifyStockSymbols(t);
            //save symbols
            
            //update is_analyzed flag
        } catch (SQLException ex) {
            Logger.getLogger(TweetAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void identifyStockSymbols(Tweet t) throws SQLException{
        if(stockSymbolMap == null){
            loadSymbolMap();
        }
        StringTokenizer st = new StringTokenizer(t.text);
        while(st.hasMoreTokens()){
            String token = st.nextToken();
            Long symbolId = stockSymbolMap.get(token);
            if(symbolId != null){
                TweetStock tweetStock = new TweetStock();
                tweetStock.stockId = symbolId;
                tweetStock.tweetId = t.id;
                tweetStockDao.insertEntity(tweetStock);
            }
        }
    } 
    
    public int determineSentiment(Tweet t){
        return sentimentAnalzer.getSentiment(t.text);
    }
    
    public boolean hasLink(Tweet t){
        return t.text.toLowerCase().contains("http:");
    }
    
    public float calculateInfluence(TwitterUser user){
        //(followers_count * (followers_count/statuses_count))
        if(user == null){
            return 0f;
        }
        
        float influence = ((float)user.followersCount * ((float)user.followersCount /(float)user.statusesCount ));
        return influence;
    }
    
    private void loadSymbolMap() throws SQLException{
        stockSymbolMap = new HashMap<>();
        List<StockSymbol> symbols = stockDao.getActiveStockSymbols();
        for (StockSymbol stockSymbol : symbols) {
            stockSymbolMap.put("$"+stockSymbol.symbol, stockSymbol.id);
        }
    }
}
