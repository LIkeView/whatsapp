package com.likeview.bulkwhatsappmsg.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserTotalVote {

    @SerializedName("session_user_total_vote")
    @Expose
    private String sessionUserTotalVote;

    public String getSessionUserTotalVote() {
        return sessionUserTotalVote;
    }

    public void setSessionUserTotalVote(String sessionUserTotalVote) {
        this.sessionUserTotalVote = sessionUserTotalVote;
    }
}
