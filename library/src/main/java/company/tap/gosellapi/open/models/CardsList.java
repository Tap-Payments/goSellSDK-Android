package company.tap.gosellapi.open.models;

import java.util.ArrayList;

import company.tap.gosellapi.internal.api.models.SavedCard;

public class CardsList {

    private int responseCode;
    private String object;
    private boolean has_more;
    private ArrayList<SavedCard> cards;


    public CardsList( int responseCode,String object,boolean has_more,ArrayList<SavedCard> data){
        this.responseCode = responseCode;
        this.object  = object;
        this.has_more = has_more;
        this.cards = data;
    }

    /**
     * Gets Response Code
      * @return responseCode
     */
    public int getResponseCode(){
        return responseCode;
    }

    /**
     *  Gets Object type
     * @return object
     */
    public String getObject() {
        return object;
    }

    /**
     *  Check if customer has more cards
     * @return has_more
     */
    public boolean isHas_more() {
        return has_more;
    }

    /**
     * Gets cards.
     *
     * @return the cards
     */
      public ArrayList<SavedCard> getCards() {

        if ( cards == null ) {

            cards = new ArrayList<>();
        }

        return cards;
    }
}
