package com.bigdata.model.UserBehavior;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "URL_BEHAVIOR")
public class UrlBehavior extends Behavior {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private Behavior behavior;

    @Embedded
    public Behavior getBehavior() {
        return behavior;
    }

    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
    }
}