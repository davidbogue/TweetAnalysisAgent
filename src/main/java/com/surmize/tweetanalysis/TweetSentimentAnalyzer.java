package com.surmize.tweetanalysis;

import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TweetSentimentAnalyzer {

    HashMap<String, Boolean> goodSentimentWords;
    HashMap<String, Boolean> badSentimentWords;

    public TweetSentimentAnalyzer() {
        populateWordMaps();
    }
    
    // 0, 1, 2, 3, 4  where 0 = very negative; 2 = neutral, 4 = very positive
    public int getSentiment(String tweet){
        int wordScore = 0;
        StringTokenizer tokenizer = new StringTokenizer(tweet.toLowerCase());
        while(tokenizer.hasMoreTokens()){
            String nextToken = tokenizer.nextToken();
            if( goodSentimentWords.get(nextToken) != null ){
                wordScore++;
            }
            else if( badSentimentWords.get(nextToken) != null ){
                wordScore--;
            }
        }
        return normalizeSentiment(wordScore);
    }

    private int normalizeSentiment(int wordScore){
        if(wordScore > 2){ return 4; }
        if(wordScore >0){ return 3; }
        if(wordScore == 0){ return 2; }
        if(wordScore > -2){ return 1; }
        return 0;
                
    }
    
    private void populateWordMaps() {
        goodSentimentWords = new HashMap<>();
        badSentimentWords = new HashMap<>();
        Scanner goodScanner = null;
        Scanner badScanner = null;

        try {
            goodScanner = new Scanner(this.getClass().getResourceAsStream("/sentiment_good.txt"));
            while (goodScanner.hasNextLine()) {
                String goodWord = goodScanner.nextLine();
                goodSentimentWords.put(goodWord, Boolean.TRUE);
            }
            
            badScanner = new Scanner(this.getClass().getResourceAsStream("/sentiment_bad.txt"));
            while (badScanner.hasNextLine()) {
                String badWord = badScanner.nextLine();
                badSentimentWords.put(badWord, Boolean.TRUE);
            }
        } finally {
            if(goodScanner != null) goodScanner.close();
            if(badScanner != null) badScanner.close();
        }

    }

}
