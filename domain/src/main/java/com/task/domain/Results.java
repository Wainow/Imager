package com.task.domain;

import java.util.ArrayList;

public class Results {
    private float total;
    private float total_pages;

    public ArrayList<Root> getResults() {
        return results;
    }

    public void setResults(ArrayList<Root> results) {
        this.results = results;
    }

    private ArrayList<Root> results = new ArrayList<>();


    // Getter Methods

    public float getTotal() {
        return total;
    }

    public float getTotal_pages() {
        return total_pages;
    }

    // Setter Methods

    public void setTotal( float total ) {
        this.total = total;
    }

    public void setTotal_pages( float total_pages ) {
        this.total_pages = total_pages;
    }
}
