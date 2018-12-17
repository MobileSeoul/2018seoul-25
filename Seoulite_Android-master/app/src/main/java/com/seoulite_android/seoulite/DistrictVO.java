package com.seoulite_android.seoulite;

public class DistrictVO {
    private String districtKr;
    private String districtEn;
    private int totalPop;
    private int foreignPop;
    private float foreignRate;
    private int avgRent;
    private int rentRank;
    private String distNearKr;
    private String distNearEn;
    private String featsKr;
    private String featsEn;

    public DistrictVO(String districtKr, String districtEn, int totalPop, int foreignPop,
                      float foreignRate, int avgRent, int rentRank, String distNearKr,
                      String distNearEn, String featsKr, String featsEn) {
        setDistrictKr(districtKr);
        setDistrictEn(districtEn);
        setTotalPop(totalPop);
        setForeignPop(foreignPop);
        setForeignRate(foreignRate);
        setAvgRent(avgRent);
        setRentRank(rentRank);
        setDistNearKr(distNearKr);
        setDistNearEn(distNearEn);
        setFeatsKr(featsKr);
        setFeatsEn(featsEn);
    }

    public String getDistrictKr() {
        return districtKr;
    }

    public void setDistrictKr(String districtKr) {
        this.districtKr = districtKr;
    }

    public String getDistrictEn() {
        return districtEn;
    }

    public void setDistrictEn(String districtEn) {
        this.districtEn = districtEn;
    }

    public int getTotalPop() {
        return totalPop;
    }

    public void setTotalPop(int totalPop) {
        this.totalPop = totalPop;
    }

    public int getForeignPop() {
        return foreignPop;
    }

    public void setForeignPop(int foreignPop) {
        this.foreignPop = foreignPop;
    }

    public float getForeignRate() {
        return foreignRate;
    }

    public void setForeignRate(float foreignRate) {
        this.foreignRate = foreignRate;
    }

    public int getAvgRent() {
        return avgRent;
    }

    public void setAvgRent(int avgRent) {
        this.avgRent = avgRent;
    }

    public int getRentRank() {
        return rentRank;
    }

    public void setRentRank(int rentRank) {
        this.rentRank = rentRank;
    }

    public String getDistNearKr() {
        return distNearKr;
    }

    public void setDistNearKr(String distNearKr) {
        this.distNearKr = distNearKr;
    }

    public String getDistNearEn() {
        return distNearEn;
    }

    public void setDistNearEn(String distNearEn) {
        this.distNearEn = distNearEn;
    }

    public String getFeatsKr() {
        return featsKr;
    }

    public void setFeatsKr(String featsKr) {
        this.featsKr = featsKr;
    }

    public String getFeatsEn() {
        return featsEn;
    }

    public void setFeatsEn(String featsEn) {
        this.featsEn = featsEn;
    }

    @Override
    public String toString() {
        return "DistrictVO{" +
                "districtKr='" + districtKr + '\'' +
                ", districtEn='" + districtEn + '\'' +
                ", totalPop=" + totalPop +
                ", foreignPop=" + foreignPop +
                ", foreignRate=" + foreignRate +
                ", avgRent=" + avgRent +
                ", rentRank=" + rentRank +
                ", distNearKr='" + distNearKr + '\'' +
                ", distNearEn='" + distNearEn + '\'' +
                ", featsKr='" + featsKr + '\'' +
                ", featsEn='" + featsEn + '\'' +
                '}';
    }
}
