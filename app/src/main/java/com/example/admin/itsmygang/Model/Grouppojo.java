package com.example.admin.itsmygang.Model;


public class Grouppojo
{
    private String name;
    private String creator;
    private int members;
    private String photo_url;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Grouppojo() {
    }

    public Grouppojo(String name, String creator, int members, String photo_url) {
        this.name = name;
        this.creator = creator;
        this.members = members;
        this.photo_url = photo_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

}
