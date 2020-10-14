package com.example.emsapp.Model;

public class FeedBack {
    private String pushId;
    private String feedBackTime;
    private String feedBackSender;
    private String feedBackText;

    public FeedBack() {
    }

    public FeedBack(String pushId, String feedBackTime, String feedBackSender, String feedBackText) {
        this.pushId = pushId;
        this.feedBackTime = feedBackTime;
        this.feedBackSender = feedBackSender;
        this.feedBackText = feedBackText;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getFeedBackTime() {
        return feedBackTime;
    }

    public void setFeedBackTime(String feedBackTime) {
        this.feedBackTime = feedBackTime;
    }

    public String getFeedBackSender() {
        return feedBackSender;
    }

    public void setFeedBackSender(String feedBackSender) {
        this.feedBackSender = feedBackSender;
    }

    public String getFeedBackText() {
        return feedBackText;
    }

    public void setFeedBackText(String feedBackText) {
        this.feedBackText = feedBackText;
    }
}
