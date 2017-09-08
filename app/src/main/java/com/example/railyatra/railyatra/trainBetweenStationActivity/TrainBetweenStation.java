package com.example.railyatra.railyatra.trainBetweenStationActivity;

public class TrainBetweenStation {
    String trainName;
    String fromCode;
    String toCode;
    String depatureTime;
    String arrivalTime;
    String travelTime;

    public TrainBetweenStation(String trainName, String fromCode, String toCode, String depatureTime, String arrivalTime, String travelTime) {
        this.trainName = trainName;
        this.fromCode = fromCode;
        this.toCode = toCode;
        this.depatureTime = depatureTime;
        this.arrivalTime = arrivalTime;
        this.travelTime = travelTime;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getFromCode() {
        return fromCode;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode;
    }

    public String getToCode() {
        return toCode;
    }

    public void setToCode(String toCode) {
        this.toCode = toCode;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepatureTime() {
        return depatureTime;
    }

    public void setDepatureTime(String depatureTime) {
        this.depatureTime = depatureTime;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }
}
