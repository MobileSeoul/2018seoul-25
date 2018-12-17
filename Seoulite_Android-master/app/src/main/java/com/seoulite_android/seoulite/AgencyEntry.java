package com.seoulite_android.seoulite;

public class AgencyEntry {
    String name;
    String representative;
    String tel;
    String fax;
    String address_full;
    String address_district;
    String language;

    public AgencyEntry(String name, String representative, String tel, String fax, String address_full, String address_district, String language) {
        this.name = name;
        this.representative = representative;
        this.tel = tel;
        this.fax = fax;
        this.address_full = address_full;
        this.address_district = address_district;
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepresentative() {
        return representative;
    }

    public void setRepresentative(String representative) {
        this.representative = representative;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAddress_full() {
        return address_full;
    }

    public void setAddress_full(String address_full) {
        this.address_full = address_full;
    }

    public String getAddress_district() {
        return address_district;
    }

    public void setAddress_district(String address_district) {
        this.address_district = address_district;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
