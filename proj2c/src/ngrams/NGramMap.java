package ngrams;

import java.util.Collection;
import java.util.TreeMap;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;
import edu.princeton.cs.algs4.In;

//import static utils.Utils.*;
/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    private TimeSeries WordsCount;
    private TreeMap<String, TimeSeries> wordsData;
    private In in;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        TimeSeries recordOfWord = new TimeSeries();
        this.wordsData = new TreeMap<>();
        this.in = new In(wordsFilename);
        while(!in.isEmpty())
        {
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split("\t");
            String word = splitLine[0];
            if(!this.wordsData.containsKey(word)) {
                recordOfWord = new TimeSeries();
            }
            Integer year = Integer.valueOf(splitLine[1]);
            Double wordCount = Double.valueOf(splitLine[2]);
            recordOfWord.put(year,wordCount);
            this.wordsData.put(word,recordOfWord);
        }
        this.WordsCount = new TimeSeries();
        this.in = new In(countsFilename);
        while (!in.isEmpty())
        {
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split(",");
            Integer year = Integer.valueOf(splitLine[0]);
            Double wordCount = Double.valueOf(splitLine[1]);
            this.WordsCount.put(year,wordCount);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries temp = this.wordsData.get(word);
        if(temp == null)
            return new TimeSeries();
        return new TimeSeries(temp,startYear,endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
            TimeSeries countHistory = new TimeSeries();
            TimeSeries temp = this.wordsData.get(word);
            countHistory = loop(countHistory,temp);
            temp = null;
        return countHistory;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.

        return new TimeSeries(this.WordsCount,MIN_YEAR,MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        if(!this.wordsData.containsKey(word))
            return new TimeSeries();
        TimeSeries WORD = new TimeSeries(this.wordsData.get(word),startYear,endYear);
        TimeSeries Total = new TimeSeries(this.WordsCount,startYear,endYear);
        return WORD.dividedBy(Total);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        if(!this.wordsData.containsKey(word))
            return new TimeSeries();
        TimeSeries temp = this.wordsData.get(word);
        TimeSeries WORD = new TimeSeries();
        WORD = loop(WORD,temp);
        TimeSeries count = this.WordsCount;
        TimeSeries Total = new TimeSeries();
        Total = loop(Total,count);
        return WORD.dividedBy(Total);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries sum = new TimeSeries();
        TimeSeries temp = new TimeSeries();
        for(String i : words)
        {
            temp = weightHistory(i,startYear,endYear);
            sum = sum.plus(temp);
        }
        return sum;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.
        TimeSeries sum = new TimeSeries();
        TimeSeries temp = new TimeSeries();
        for(String i : words)
        {
            temp = weightHistory(i);
            sum = sum.plus(temp);
        }
        return sum;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
    private TimeSeries loop(TimeSeries target, TimeSeries source)
    {
        for(Integer i : source.keySet())
        {
            target.put(i,source.get(i));
        }
        return target;
    }
}
