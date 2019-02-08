package com.bigdata.model.System;

import com.bigdata.model.UserBehavior.Behavior;

/**
 * It is the API structure that used to every data ouput.
 */


public class Message {
    private int id;
    private String error="0";
    private String type;
    private Behavior behavior;


    public Message(){
    }


    public Message(int id, String type, Behavior behavior){
        this.id = id;
        this.type = type;
        this.behavior = behavior;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Behavior getBehavior() {
        return behavior;
    }

    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
