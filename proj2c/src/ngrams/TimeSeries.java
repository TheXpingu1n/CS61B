package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        // TODO: Fill in this constructor.
        if(startYear >= MIN_YEAR && endYear <= MAX_YEAR) {
            for (int i = startYear; i <= endYear; i++) {
                if(ts.get(i) == null)
                    continue;
                this.put(i, ts.get(i));
            }
        }
        else {
            for (int i = MIN_YEAR; i <= MAX_YEAR; i++) {
                if(ts.get(i) == null)
                    continue;
                this.put(i, ts.get(i));
            }
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        // TODO: Fill in this method.
        return new ArrayList<>(this.keySet());
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        // TODO: Fill in this method.
        return new ArrayList<>(this.values());
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        // TODO: Fill in this method.
        double res;
        if(ts.isEmpty() && super.isEmpty())
            return new TimeSeries();
        TimeSeries newTime = new TimeSeries();
        populateKeys(ts,newTime);
        for(Integer i : newTime.keySet())
        {
            res = binarySearch(i,this) + binarySearch(i,ts);
            newTime.put(i,res);
        }
        return newTime;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        // TODO: Fill in this method.
        if(!equalTSyears(this,ts))
            throw new IllegalArgumentException();
        Double res;
        TimeSeries newMap = new TimeSeries();
        for(Integer i : this.keySet())
        {
            if(!ts.containsValue(null) && !this.containsValue(null)) {
                res = this.get(i) / ts.get(i);
                newMap.put(i,res);
            }
        }
        return newMap;
    }
    public long totalCountsOfTimeSeries()
    {
        long totalCount = 0;
        for(Integer i : this.keySet())
        {
            double getter = this.get(i);
            totalCount += (long) getter;
        }
        return totalCount;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
    private double binarySearch(int key, TimeSeries ts)
    {
        Double res = ts.get(key);
        if(res == null)
            return 0;
        return res;
    }
    private void populateKeys(TimeSeries ts, TimeSeries newTime)
    {
        for(Integer i : ts.keySet())
        {
            newTime.put(i,null);
        }
        for(Integer i : this.keySet())
        {
            newTime.put(i,null);
        }
    }
    private boolean equalTSyears(TimeSeries ts1, TimeSeries ts2)
    {
        for(Integer i : ts1.keySet())
        {
            if(!ts2.containsKey(i))
            {
                return false;
            }
        }
        return true;
    }
}
