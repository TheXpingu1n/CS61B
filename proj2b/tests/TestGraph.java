import edu.princeton.cs.algs4.In;
import main.Graph;
import main.WordNet;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class TestGraph {
    @Test
    public void TestIndicesOfWordMapper()
    {
        Graph g = new Graph(17);
        String synsetFile = "./data/wordnet/synsets16.txt";
        String hyponymFile = "./data/wordnet/hyponyms16.txt";
        In inSyn = new In(synsetFile);
        In inHyp = new In(hyponymFile);
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
      //  ArrayList<Integer> output = (ArrayList<Integer>) g.indicesOfaWord("change");
      //  output.addAll(g.indicesOfaWord("occurrence"));
      //  assertThat(output).isEqualTo(List.of(2,8,1));
       // assertThat(g.hyponymsNodes(List.of("change","occurrence"))).isEqualTo("");
      //  assertThat(g.hyponymsWords(List.of("change","occurrence"))).isEqualTo("");
    }
}
