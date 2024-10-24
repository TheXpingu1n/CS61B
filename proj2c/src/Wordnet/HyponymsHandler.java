package Wordnet;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import org.checkerframework.checker.units.qual.K;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

public class HyponymsHandler extends NgordnetQueryHandler {

    private WordNet net;
    private NGramMap ngram;
    public HyponymsHandler(WordNet net, NGramMap ngram)
    {
        this.net = net;
        this.ngram = ngram;
    }
    @Override
    public String handle(NgordnetQuery q) {

        if(q.k() == 0)
            return net.Start(q.words()).toString();

        return K_handler(q).toString();
    }
    private ArrayList<String> K_handler(NgordnetQuery q)
    {
        ArrayList<String> wordsBeforeK = (ArrayList<String>) net.Start(q.words());
        HashMap<Long, String> wordsWithTheirCount = new HashMap<>();
        int startYear = q.startYear();
        int endYear = q.endYear();
        for(String i : wordsBeforeK)
        {
            long count = ngram.countHistory(i,startYear, endYear).totalCountsOfTimeSeries();
            wordsWithTheirCount.put(count, i);
        }
        PriorityQueue<Long> counts = new PriorityQueue<>(Collections.reverseOrder());
        counts.addAll(wordsWithTheirCount.keySet());
        int k = q.k();
        if(counts.isEmpty() || counts.peek() == 0)
            return new ArrayList<>();
        ArrayList<String> wordsAfterK = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if(!counts.isEmpty() && counts.peek() != 0)
            {
                wordsAfterK.add(wordsWithTheirCount.get(counts.poll()));
            }
        }
        return wordsAfterK;

    }
}
