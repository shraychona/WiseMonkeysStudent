package com.shray.wisemonkeysstudent.model;

/**
 * Created by Shray on 5/24/2017.
 */

public class Offer {
    private String placeName;
    private String offerDetail;

    public Offer(String placeName,String offerDetail) {
        this.placeName=placeName;
        this.offerDetail=offerDetail;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getOfferDetail() {
        return offerDetail;
    }

    public void setOfferDetail(String offerDetail) {
        this.offerDetail = offerDetail;
    }
}
