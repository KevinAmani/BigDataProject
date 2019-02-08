package com.bigdata.model.UserBehavior;

/**
 * This entity represents the user's behavior of  selecting.
 */

import javax.persistence.*;

@Entity
@Table(name = "SELECT_BEHAVIOR")
public class SelectBehavior{

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
