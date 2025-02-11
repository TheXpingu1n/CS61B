package Wordnet;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetQueryType;
import ngrams.NGramMap;
import org.checkerframework.checker.units.qual.K;

import java.util.*;

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
            return net.Start(q.words(),q.ngordnetQueryType()).toString();
        ArrayList<String> result = K_handler(q);
        Collections.sort(result);
        return result.toString();

    }
    private ArrayList<String> K_handler(NgordnetQuery q)
    {
        ArrayList<String> wordsBeforeK = (ArrayList<String>) net.Start(q.words(),q.ngordnetQueryType());
        HashMap<String,Long> wordsWithTheirCount = new HashMap<>();
        ArrayList<String> wordsAfterK = new ArrayList<>();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();

        for(String i : wordsBeforeK)
        {
            long count = ngram.countHistory(i,startYear, endYear).totalCountsOfTimeSeries();
            wordsWithTheirCount.put(i,count);
        }

        ArrayList<Long> counts = new ArrayList<>(wordsWithTheirCount.values());
        Collections.sort(counts);

        if(counts.isEmpty() || counts.getLast() == 0)
            return new ArrayList<>();

        for (int i = 0; i < k; i++) {
            if(!counts.isEmpty() && counts.getLast() != 0)
            {
                for(String j : wordsWithTheirCount.keySet())
                {
                    if(wordsWithTheirCount.get(j).equals(counts.getLast()))
                    {
                        wordsAfterK.add(j);
                        wordsWithTheirCount.remove(j,counts.removeLast());
                        break;
                    }
                }
            }
        }

        return wordsAfterK;
    }

}
