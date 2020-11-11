package com.sukanta.knowyourgoverment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Official implements Serializable {
    private String officialCity;
    private String officialState;
    private String officialZip;

    private String officeName;
    private int officialIndex;


    private String officialName;
    private String officialAddress;
    private String party; // omitted
    private String phone; // omitted use first one
    private String url; // omitted use first one
    private String email; // omitted use first one
    private String photoUrl; // omitted, default place holder
    private String facebookId; // omitted
    private String twitterId; // omitted
    private String youTubeId; // omitted

    public String getOfficialCity() {
        return officialCity == null ? "" : officialCity;
    }

    public void setOfficialCity(String officialCity) {
        this.officialCity = officialCity;
    }

    public String getOfficialState() {
        return officialState == null ? "" : officialState;
    }

    public void setOfficialState(String officialState) {
        this.officialState = officialState;
    }

    public String getOfficialZip() {
        return officialZip == null ? "" : officialZip;
    }

    public void setOfficialZip(String zip) {
        this.officialZip = zip;
    }

    public String getOfficeName() {
        return officeName == null ? "" : officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getOfficialName() {
        return officialName == null ? "" : officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public String getOfficialAddress() {
        return officialAddress == null ? "" : officialAddress;
    }

    public void setOfficialAddress(String officialAddress) {
        this.officialAddress = officialAddress;
    }

    public String getParty() {
        return party == null ? "" : party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getPhone() {
        return phone == null ? "" : phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email == null ? "" : email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl == null ? "" : photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getFacebookId() {
        return facebookId == null ? "" : facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getTwitterId() {
        return twitterId == null ? "" : twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }

    public String getYouTubeId() {
        return youTubeId == null ? "" : youTubeId;
    }

    public void setYouTubeId(String youTubeId) {
        this.youTubeId = youTubeId;
    }

    public int getOfficialIndex() {
        return officialIndex;
    }

    public void setOfficialIndex(int officialIndex) {
        this.officialIndex = officialIndex;
    }
}
