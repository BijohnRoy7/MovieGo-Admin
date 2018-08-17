package com.donate_blood.invenz.moviegoadmin.models;

public class MovieRequest {

    private int id;
    private String movieName, catagory, requestNo;

    public MovieRequest(int id, String movieName, String catagory, String requestNo) {
        this.id = id;
        this.movieName = movieName;
        this.catagory = catagory;
        this.requestNo = requestNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }
}
