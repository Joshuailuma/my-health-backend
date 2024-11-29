package com.myhealth.customer.entity;


public class UserDetails {
    private User user;
    private Object details;

    public UserDetails(User user, Object details){
        this.user = user;
        this.details = details;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
