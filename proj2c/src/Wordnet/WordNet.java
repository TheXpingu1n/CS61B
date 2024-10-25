package Wordnet;

import Wordnet.Graph;
import browser.NgordnetQueryType;
import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {
    private Graph g;
    private In inSyn;
    private In inHyp;
    public WordNet(String synsets, String hyponyms)
    {
        inSyn = new In(synsets);
        inHyp = new In(hyponyms);
        ArrayList<String> syn = new ArrayList<>();
        while(!inSyn.isEmpty())
        {
            String line = inSyn.readLine();
            String[] split = line.split(",");
            syn.add(split[1]);
        }
        g = new Graph(syn.size());
        g.addNodes(syn);
        while(!inHyp.isEmpty())
        {
            String nodes = inHyp.readLine();
            String[] split = nodes.split(",");
            int parent = Integer.parseInt(split[0]);
            for (int i = 1; i < split.length; i++) {
                int valueOfSplit = Integer.parseInt(split[i]);
                g.addEdge(parent,valueOfSplit);
            }
        }

    }
    public List<String> Start(List<String> stream, NgordnetQueryType queryType)
    {
        HashSet<HashSet<String>> setsOfWords = g.hyponymsWords(stream,queryType);
        if(setsOfWords.isEmpty())
            return new ArrayList<String>();
        HashSet<String> result = new HashSet<>(setsOfWords.iterator().next());
        for(HashSet<String> i : setsOfWords)
        {
            result.retainAll(i);
        }
        List<String> finalres = new ArrayList<>(result);
        Collections.sort(finalres);
        return finalres;
    }


}
