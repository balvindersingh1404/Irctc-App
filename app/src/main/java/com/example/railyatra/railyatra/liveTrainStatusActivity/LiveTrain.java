package com.example.railyatra.railyatra.liveTrainStatusActivity;

public class LiveTrain {
    String trainStationInfo;
    String sch_arrive;
    String act_arrive;
    String sch_depature;
    String act_depature;
    String latemin;

    public LiveTrain(String sch_arrive, String trainStationInfo, String act_arrive, String sch_depature, String act_depature, String latemin) {
        this.sch_arrive = sch_arrive;
        this.trainStationInfo = trainStationInfo;
        this.act_arrive = act_arrive;
        this.sch_depature = sch_depature;
        this.act_depature = act_depature;
        this.latemin = latemin;
    }

    public String getTrainStationInfo() {
        return trainStationInfo;
    }

    public void setTrainStationInfo(String trainStationInfo) {
        this.trainStationInfo = trainStationInfo;
    }

    public String getSch_arrive() {
        return sch_arrive;
    }

    public void setSch_arrive(String sch_arrive) {
        this.sch_arrive = sch_arrive;
    }

    public String getAct_arrive() {
        return act_arrive;
    }

    public void setAct_arrive(String act_arrive) {
        this.act_arrive = act_arrive;
    }

    public String getSch_depature() {
        return sch_depature;
    }

    public void setSch_depature(String sch_depature) {
        this.sch_depature = sch_depature;
    }

    public String getAct_depature() {
        return act_depature;
    }

    public void setAct_depature(String act_depature) {
        this.act_depature = act_depature;
    }

    public String getLatemin() {
        return latemin;
    }

    public void setLatemin(String latemin) {
        this.latemin = latemin;
    }
}
