package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    NGramMap map;
    public HistoryHandler(NGramMap map)
    {
        this.map = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        TimeSeries plot;
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> legend = new ArrayList<>();
        for(String i : words)
        {
            plot = this.map.weightHistory(i,startYear,endYear);
            lts.add(plot);
            legend.add(i);
        }
        XYChart chart = Plotter.generateTimeSeriesChart(legend, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);
        return encodedImage;
    }
}
