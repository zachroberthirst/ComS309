package com.example.frontend.model.user;

import com.example.frontend.model.match.Match;

import java.util.Comparator;

/**
 * User message cards to be displayed on messaging page
 */
public class UserMessageCard {
    private String imageUrl;
    private Match match;

    /**
     * New user message card.
     * @param imageUrl profile picture url
     * @param match match object
     */
    public UserMessageCard(String imageUrl, Match match){
        this.imageUrl = imageUrl;
        this.match = match;
    }

    /**
     * Get Image url.
     * @return url of profile picture
     */
    public String getImageUrl(){
        return imageUrl;
    }

    /**
     * Set image url.
     * @param imageUrl url to be set
     */
    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    /**
     * Get match object.
     * @return match
     */
    public Match getMatch(){
        return match;
    }

    /**
     * Set match.
     * @param match match to be set
     */
    public void setMatch(Match match){
        this.match = match;
    }

    public static Comparator<UserMessageCard> userMessageCardComparator = new Comparator<UserMessageCard>() {
        @Override
        public int compare(UserMessageCard card1, UserMessageCard card2) {
           return  Match.matchComparator.compare(card1.getMatch(), card2.getMatch());
        }
    };

}
