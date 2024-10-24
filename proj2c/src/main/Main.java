package main;

import Wordnet.HyponymsHandler;
import Wordnet.WordNet;
import browser.NgordnetServer;
import ngrams.NGramMap;
import org.slf4j.LoggerFactory;

public class Main {
    static {
        LoggerFactory.getLogger(Main.class).info("\033[1;38mChanging text color to white");
    }
    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();
        
        // The following code might be useful to you.

        String wordFile = "./data/ngrams/top_14377_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";
        NGramMap ngm = new NGramMap(wordFile, countFile);
        WordNet net = new WordNet();


        hns.startUp();
        hns.register("history", new HistoryHandler());
        hns.register("historytext", new HistoryTextHandler());
        hns.register("hyponyms", new HyponymsHandler());

        System.out.println("Finished server startup! Visit http://localhost:4567/ngordnet.html");
    }
}
