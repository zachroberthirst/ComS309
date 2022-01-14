package com.example.frontend.model.match;

import com.example.frontend.model.listing.Listing;
import com.example.frontend.model.user.User;
import java.util.Comparator;
import java.util.Date;
import java.util.function.Function;

public class Match{
    private int id =-1;
    private Long timestamp;
    private User user;
    private Listing listing;
    private boolean adorableAction;

    public Match(User user, Listing listing){
        this.user = user;
        this.listing = listing;
        Date date = new Date();
        timestamp = date.getTime();
    }
    public Match(){
        Date date = new Date();
        timestamp = date.getTime();
    }


    public User getUser(){
        return user;
    }
    public Listing getListing(){
        return listing;
    }
    public Long getTimestamp(){
        return timestamp;
    }
    public int getId(){
        return this.id;
    }
    public boolean getAdorableAction(){return this.adorableAction;}

    public void setId(int id) {
        this.id = id;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAdorableAction(boolean adorableAction) {
        this.adorableAction = adorableAction;
    }


    @Override
    public String toString(){
        return ("Match: "+id +
                ", User: "+user+
                ", Listing: "+listing+
                ", Timestamp: "+timestamp);
    }

    public static Comparator<Match> matchComparator = new Comparator<Match>() {
        @Override
        public int compare(Match match1, Match match2) {
            if(match1.equals(match2)){
                return 0;
            }
            if(match1.getAdorableAction() ^ match2.getAdorableAction()){
                if(match2.getAdorableAction()){
                    return 1;
                }
                else{
                    return -1;
                }
            }

            return match2.getTimestamp().compareTo(match1.getTimestamp());
        }
    };

    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        if (!(o instanceof Match)) {
            return false;
        }
        if(id == ((Match) o).getId()){
            return true;
        }
        return user.getId() == ((Match) o).getUser().getId() && listing.getId() == ((Match) o).getListing().getId();
    }
}
