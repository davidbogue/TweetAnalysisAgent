package com.surmize.tweetanalysis;

import org.junit.Test;
import static org.junit.Assert.*;

public class TweetSentimentAnalyzerTest {
    
    public TweetSentimentAnalyzerTest() {
    }

    /**
     * Test of getSentiment method, of class TweetSentimentAnalyzer.
     */
    @Test
    public void testGetSentiment() {
        String goodTest = "good buy outperform";
        String oktest = "buy blah blah blah";
        String nuetralTest = "blah blah blah";
        String badTest = "Sell blah blah blah";
        String horribleTest = "Sell short put bad";
        
        TweetSentimentAnalyzer tsa = new TweetSentimentAnalyzer();
        
        assertTrue( 4 == tsa.getSentiment(goodTest));
        assertTrue( 3 == tsa.getSentiment(oktest));
        assertTrue( 2 == tsa.getSentiment(nuetralTest));
        assertTrue( 1 == tsa.getSentiment(badTest));
        assertTrue( 0 == tsa.getSentiment(horribleTest));
    }
    
}
