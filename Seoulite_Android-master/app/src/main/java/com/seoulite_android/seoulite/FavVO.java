package com.seoulite_android.seoulite;

public class FavVO {
    private int id;
    private String name;
    private int isDistrict;
    private int isAgency;
    private String memo;

    public FavVO(int id, String name, int isDistrict, int isAgency, String memo) {
        this.id = id;
        this.name = name;
        this.isDistrict = isDistrict;
        this.isAgency = isAgency;
        this.memo = memo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsDistrict() {
        return isDistrict;
    }

    public void setIsDistrict(int isDistrict) {
        this.isDistrict = isDistrict;
    }

    public int getIsAgency() {
        return isAgency;
    }

    public void setIsAgency(int isAgency) {
        this.isAgency = isAgency;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
