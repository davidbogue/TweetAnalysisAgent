package com.surmize.tweetanalysis;

import com.surmize.dao.TweetProcessQueueDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalysisThread implements Runnable {
    
    private final TweetProcessQueueDAO processQueue;
    private int sleepInSeconds = 1;

    public AnalysisThread() {
        processQueue = new TweetProcessQueueDAO();
    }
    
    @Override
    public void run() {
        while(true){
            try {
                Long tweetId = processQueue.getNextTweetId();
                if(tweetId != null){
                    sleepInSeconds = 1; // reset Sleep time
                    
                } else {
                    System.out.println("Nothing to process.. going to sleep for a bit");
                    Thread.sleep(getSleepTime());
                }
            } catch (InterruptedException | SQLException ex) {
                Logger.getLogger(AnalysisThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    // increment the sleep time until you get to 64 seconds... this is to prevent unneeded polling when there is nothing to process
    private int getSleepTime(){
        int millis = sleepInSeconds * 1000;
        if(sleepInSeconds < 64){
            sleepInSeconds = sleepInSeconds * 2;
        }
        return millis;
    }

    
   
}
