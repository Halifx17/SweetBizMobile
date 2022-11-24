package com.example.sweetbizmobile;

import android.widget.Filter;

import java.util.ArrayList;

public class FilterStat extends Filter {

    private StarAdapter starAdapter;
    private ArrayList<Products> filterList;

    public FilterStat(StarAdapter starAdapter, ArrayList<Products> filterList) {
        this.starAdapter = starAdapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        FilterResults results = new FilterResults();

        if(constraint != null && constraint.length() > 0){

            constraint = constraint.toString().toUpperCase();

            ArrayList<Products> filteredModels = new ArrayList<>();
            for(int i=0; i<filterList.size(); i++) {

                if (filterList.get(i).getName().toUpperCase().contains(constraint)||filterList.get(i).getCategory().toUpperCase().contains(constraint)) {

                    filteredModels.add(filterList.get(i));
                }
            }

            results.count = filteredModels.size();
            results.values = filteredModels;
        }
        else{
            results.count = filterList.size();
            results.values = filterList;

        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults results) {

        starAdapter.list = (ArrayList<Products>) results.values;

        starAdapter.notifyDataSetChanged();

    }
}
