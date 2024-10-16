package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.ArrayList;

public class HyponymsHandler extends NgordnetQueryHandler {

    private WordNet net;
    public HyponymsHandler(WordNet net)
    {
        this.net = net;
    }
    @Override
    public String handle(NgordnetQuery q) {

        return net.Start(q.words()).toString();
    }
}
