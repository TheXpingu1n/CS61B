package main;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {
    private Graph g;
    private In inSyn;
    private In inHyp;
    private int words_needed;
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
    public List<String> Start(List<String> stream)
    {
        words_needed = stream.size();

        return CleanStart(words_needed,g.hyponymsWords(stream));
    }
    public List<String> CleanStart(int common, List<String> stream) {
        HashMap<String, Integer> wordsWithRepitions = new HashMap<>();
        for (int i = 0; i < stream.size(); i++) {
            wordsWithRepitions.put(stream.get(i), 0);
        }
        for (String i : wordsWithRepitions.keySet())
        {
            for (String j : stream)
            {
                if(i.equals(j))
                {
                    wordsWithRepitions.put(i,wordsWithRepitions.get(i)+1);
                }
            }
        }
        List<String> cleaned_results = new ArrayList<>();
        for(String i : wordsWithRepitions.keySet())
        {
            if(wordsWithRepitions.get(i) >= common)
                cleaned_results.add(i);
        }
        Collections.sort(cleaned_results);
        return cleaned_results;
    }

}
