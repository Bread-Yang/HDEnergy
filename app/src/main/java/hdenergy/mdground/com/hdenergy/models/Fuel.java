package hdenergy.mdground.com.hdenergy.models;

import java.util.ArrayList;

/**
 * Created by yoghourt on 6/27/16.
 */

public class Fuel {

    private ArrayList<Feedstock> feedstocks;

    public ArrayList<Feedstock> getFeedstocks() {
        return feedstocks;
    }

    public void setFeedstocks(ArrayList<Feedstock> feedstocks) {
        this.feedstocks = feedstocks;
    }
}
