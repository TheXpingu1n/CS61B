package main;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class Graph {
    private ArrayList<Integer>[] adjList;
    private ArrayList<String> Nodes;
    private HashMap<String,ArrayList<Integer>> wordMap;
    private boolean[] marked;
    private int[] edgeTo;
    private int Vsize;
    private int Esize;
    public Graph(int size)
    {
        adjList = new ArrayList[size];
        marked = new boolean[size];
        edgeTo = new int[size];
        Nodes = new ArrayList<>();
        wordMap = new HashMap<>();

    }
    public void addEdge(int s, int t)
    {
        if(adjList[s] == null)
            adjList[s] = new ArrayList<>();
        adjList[s].add(t);
        Esize++;
    }
    public void addNodes(List<String> nodes)
    {
        for(String i : nodes)
        {
            Nodes.add(i);
            Vsize++;
        }
        wordMapper(Nodes);
    }
    public Iterable<Integer> adj(int j)
    {
        if(adjList[j] == null)
            return new ArrayList<>();
        return adjList[j];
    }
    public int V()
    {
        return this.Vsize;
    }
    public int E()
    {
        return this.Esize;
    }
   private void wordMapper(List<String> nodes)
   {
       for (int i = 0; i < nodes.size(); i++) {
           String[] split = nodes.get(i).split(" ");
           for (int j = 0; j < split.length; j++) {
               wordMap.computeIfAbsent(split[j], k -> new ArrayList<>());
               wordMap.get(split[j]).add(i);
           }
       }
   }
   public List<Integer> indicesOfaWord(String word)
   {
       return wordMap.get(word);
   }
   public List<String> hyponymsWords(List<String> words)
   {
       ArrayList<String> NodeResult = hyponymsNodes(words);
       ArrayList<String> UnCleanWordResult = new ArrayList<>();
       for(String i : NodeResult)
       {
           String[] split = i.split(" ");
           UnCleanWordResult.addAll(Arrays.asList(split));
       }
       return UnCleanWordResult;
   }
   public ArrayList<String> hyponymsNodes(List<String> words)
   {
       ArrayList<Integer> indices = new ArrayList<>();
       ArrayList<String> Uncleaned_results = new ArrayList<>();
       for (String i : words)
       {
           indices.addAll(indicesOfaWord(i));
       }
       for(Integer i : indices)
       {
           bfs(i,Uncleaned_results);
       }
       return Uncleaned_results;
   }
   private ArrayList<String> bfs(int s, ArrayList<String> U_res)
   {
       Queue<Integer> fringe = new ArrayDeque<>();
       for (int i = 0; i < marked.length; i++) {
           edgeTo[i] = 0;
           marked[i] = false;
       }
       marked[s] = true;
       fringe.offer(s);
       U_res.add(Nodes.get(s));
       while(!fringe.isEmpty())
       {
           int v = fringe.poll();
           for(int w : adj(v))
           {
               if(!marked[w])
               {
                   fringe.offer(w);
                   marked[w] = true;
                   edgeTo[w] = v;
                   U_res.add(Nodes.get(w));
               }
           }
       }

       return U_res;
   }
}
