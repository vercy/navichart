package com.cloverleaf.navigator.chart;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DateTimeGrid implements Grid<LocalDateTime> {
    private List<? extends LocalDateTime> data;

    public DateTimeGrid(List<LocalDateTime> data)
    {
        setData(data);
    }

    @Override
    public void setData(List<? extends LocalDateTime> data) {
        if(data != null && data.size() < 3)
            return;
        this.data = data;
    }

    @Override
    public List<? extends LocalDateTime> getData() {
        return data;
    }

    @Override
    public LocalDateTime getNearestObjectFor(double val) {
        if(data != null)
        {
            return  data.get((int)(getNearestValueFor(val)*(data.size()-1)));
        }
        return null;
    }

    @Override
    public double getNearestValueFor(LocalDateTime val) {
        return getNearestValueFor(getValueFor(val));
    }

    @Override
    public double getNearestValueFor(double val) {
        if(data == null)
            return -1;

        if(val < 0.0)
            return 0.0;
        if(val > 1.0)
            return 1.0;

        int approx = (int)(val*(data.size()-1));
        return (double)approx/(data.size()-1);
    }

    @Override
    public double getValueFor(LocalDateTime o) {
        if(data == null)
            return -1;

        int len = data.size();
        int i = 0;
        while(i < len && data.get(i).compareTo(o) < 0)
            i++;

        if(i != len && data.get(i).compareTo(o) == 0) {
            return (double) i / (len - 1);
        }

        if(i == 0)
            i++;
        if(i == len)
            i--;

        double ratio = Duration.between(o, data.get(i-1)).toMillis() /
                (double)Duration.between(data.get(i), data.get(i-1)).toMillis();
        return (double)(i-1)/(len-1)+(ratio*((double)i/(len-1) - (double)(i-1)/(len-1)));
    }

    @Override
    public LocalDateTime getObjectFor(double d) {
        LocalDateTime a;
        LocalDateTime b;
        if(d < 0 || 1 < d)
            return null;

        int len = data.size();
        int i = (int)(getNearestValueFor(d)*(len-1));
        if(i == len-1)
            i--;
        a = data.get(i);
        b = data.get(i+1);
        double min = (double)(i)/(len-1);
        double max = (double)(i+1)/(len-1);
        double ratio = (d-min)/(max-min);
        return a.plus((long)(ratio*(Duration.between(b, a)).toMillis()), ChronoUnit.MILLIS);
    }
}
