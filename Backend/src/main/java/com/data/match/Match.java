package com.data.match;

import com.data.listing.Listing;
import com.data.user.User;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import javax.persistence.*;

@Entity
@Table(name = "Match_Table")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotFound(action = NotFoundAction.IGNORE)
    private Long timestamp;

    @NotFound(action = NotFoundAction.IGNORE)
    private boolean adorableAction;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    private User user;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    private Listing listing;

    public Match() {

    }
    public void setUser(User user){
        this.user = user;
    }
    public User getUser(){
        return user;
    }

    public void setListing(Listing listing){
        this.listing = listing;
    }
    public Listing getListing(){return listing;}
    public Long getTimestamp(){
        return timestamp;
    }
    public void setTimestamp(Long timestamp){
        this.timestamp = timestamp;
    }

    public boolean isAdorableAction() {return adorableAction;}
    public void setAdorableAction(boolean adorableAction){this.adorableAction = adorableAction;}

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
}
