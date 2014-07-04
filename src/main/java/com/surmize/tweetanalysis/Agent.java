package com.surmize.tweetanalysis;

public class Agent {

    public static void main(String args[]){
        Agent main = new Agent();
        main.startAnalysisThread();
    }
    
    private void startAnalysisThread(){
        Thread processingThread = new Thread(new AnalysisThread());
        processingThread.start();
    }
   
}
