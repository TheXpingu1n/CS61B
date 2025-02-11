package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap map;
    public HistoryTextHandler(NGramMap map)
    {
        this.map = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        StringBuilder response = new StringBuilder();
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        TimeSeries mySeries = new TimeSeries();
        for(String i : words)
        {
            mySeries = this.map.weightHistory(i,startYear,endYear);
            response.append(i).append(": ").append(mySeries.toString()).append("\n");
        }
        return response.toString();
    }
}
