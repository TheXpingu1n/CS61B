package main;

import Wordnet.HyponymsHandler;
import Wordnet.WordNet;
import browser.NgordnetQueryHandler;
import browser.NgordnetServer;
import edu.berkeley.eecs.inst.cs61b.ngrams.StaffNGramMap;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {
        NgordnetServer hns = new NgordnetServer();
        hns.startUp();
        StaffNGramMap ngm = new StaffNGramMap(wordFile,countFile);
        hns.register("history", new HistoryHandler());
        hns.register("historytext", new HistoryTextHandler());
        WordNet wn = new WordNet(synsetFile,hyponymFile);
        return new HyponymsHandler(wn);
        //throw new RuntimeException("Please fill out AutograderBuddy.java!");
    }
}
