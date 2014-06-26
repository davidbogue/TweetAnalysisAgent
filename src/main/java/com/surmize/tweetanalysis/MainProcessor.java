package com.surmize.tweetanalysis;

public class MainProcessor {

    public static void main(String args[]){
        MainProcessor main = new MainProcessor();
        main.startAnalysisThread();
    }
    
    private void startAnalysisThread(){
        Thread processingThread = new Thread(new AnalysisThread());
        processingThread.start();
    }
   
}
