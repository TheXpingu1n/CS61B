package main;

import Wordnet.HyponymsHandler;
import Wordnet.WordNet;
import browser.NgordnetQueryHandler;
import browser.NgordnetServer;
import edu.berkeley.eecs.inst.cs61b.ngrams.StaffNGramMap;
import ngrams.NGramMap;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {
        NgordnetServer hns = new NgordnetServer();
        hns.startUp();
        NGramMap ngm = new NGramMap(wordFile,countFile);
        hns.register("history", new HistoryHandler(ngm));
        hns.register("historytext", new HistoryTextHandler(ngm));
        WordNet wn = new WordNet(synsetFile,hyponymFile);
        return new HyponymsHandler(wn,ngm);
        //throw new RuntimeException("Please fill out AutograderBuddy.java!");
    }
}
