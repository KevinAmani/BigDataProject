package com.bigdata.model.UserGoogleZip;


import javax.persistence.*;

@Entity
public class UserActivity {


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(length = 1000)
    protected Integer id;
    protected String type = null;
    protected String user_name = null;

    @Lob
    @Column(columnDefinition="text")
    protected String link = null;

    protected String time = null;

    @Lob
    @Column(columnDefinition="text")
    protected String content = null;

    protected String from_source = null;

    @Column(columnDefinition="text")
    protected String map_location = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrom_source() {
        return from_source;
    }

    public void setFrom_source(String from_source) {
        this.from_source = from_source;
    }

    public String getMap_location() {
        return map_location;
    }

    public void setMap_location(String map_location) {
        this.map_location = map_location;
    }
}
