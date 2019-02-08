package com.bigdata.model.UserBehavior;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.sql.Timestamp;
import java.util.Date;

@Embeddable
public class Behavior {

    @Column(name = "USER_NAME")
    protected String username;

    //The type of selecting
    @Column(name = "TYPE")
    protected String type;

    //The content of selected link
    @Column(name = "CONTENT")
    protected String text;

    @Column(name = "TIMESTAMP")
    protected Timestamp timestamp;


    public Behavior() {
    }

    public Behavior(String username, String type, String text, Timestamp timestamp) {
        this.username = username;
        this.type = type;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = (Timestamp) timestamp;
    }
}
