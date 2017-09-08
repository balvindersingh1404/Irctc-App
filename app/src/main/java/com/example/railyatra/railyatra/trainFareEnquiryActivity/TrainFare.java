package com.example.railyatra.railyatra.trainFareEnquiryActivity;

public class TrainFare {
    String code;
    String name;
    String fare;

    public TrainFare(String code, String name, String fare) {
        this.code = code;
        this.name = name;
        this.fare = fare;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }
}
