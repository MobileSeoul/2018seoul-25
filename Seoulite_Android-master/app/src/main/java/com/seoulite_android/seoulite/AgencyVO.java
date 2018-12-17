package com.seoulite_android.seoulite;

class AgencyVO {
    //부동산 정보 테이블에서 select 해오는 레코드 하나를 담는 VO(DTO)
    private String agncNmKr = "";
    private String agncNmEn = "";
    private String ownKr = "";
    private String ownEn = "";
    private String phone = "";
    private String fax = "";
    private String adrGuKr = "";
    private String adrDtKr = "";
    private String adrGuEn = "";
    private String adrDtEn = "";
    private int langEn;    //0이면 제공 X, 1이면 제공 O
    private int langCn;    //0이면 제공 X, 1이면 제공 O
    private int langJp;    //0이면 제공 X, 1이면 제공 O
    private String langEtc = "";

    public AgencyVO(String agncNmKr, String agncNmEn, String ownKr, String ownEn, String phone,
                    String fax, String adrGuKr, String adrDtKr, String adrGuEn, String adrDtEn,
                    int langEn, int langCn, int langJp, String langEtc) {
        setAgncNmKr(agncNmKr);
        setAgncNmEn(agncNmEn);
        setOwnKr(ownKr);
        setOwnEn(ownEn);
        setPhone(phone);
        setFax(fax);
        setAdrGuKr(adrGuKr);
        setAdrDtKr(adrDtKr);
        setAdrGuEn(adrGuEn);
        setAdrDtEn(adrDtEn);
        setLangEn(langEn);
        setLangCn(langCn);
        setLangJp(langJp);
        setLangEtc(langEtc);
    }

    public String getAgncNmKr() {
        return agncNmKr;
    }

    public void setAgncNmKr(String agncNmKr) {
        this.agncNmKr = agncNmKr;
    }

    public String getAgncNmEn() {
        return agncNmEn;
    }

    public void setAgncNmEn(String agncNmEn) {
        this.agncNmEn = agncNmEn;
    }

    public String getOwnKr() {
        return ownKr;
    }

    public void setOwnKr(String ownKr) {
        this.ownKr = ownKr;
    }

    public String getOwnEn() {
        return ownEn;
    }

    public void setOwnEn(String ownEn) {
        this.ownEn = ownEn;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAdrGuKr() {
        return adrGuKr;
    }

    public void setAdrGuKr(String adrGuKr) {
        this.adrGuKr = adrGuKr;
    }

    public String getAdrDtKr() {
        return adrDtKr;
    }

    public void setAdrDtKr(String adrDtKr) {
        this.adrDtKr = adrDtKr;
    }

    public String getAdrGuEn() {
        return adrGuEn;
    }

    public void setAdrGuEn(String adrGuEn) {
        this.adrGuEn = adrGuEn;
    }

    public String getAdrDtEn() {
        return adrDtEn;
    }

    public void setAdrDtEn(String adrDtEn) {
        this.adrDtEn = adrDtEn;
    }

    public int getLangEn() {
        return langEn;
    }

    public void setLangEn(int langEn) {
        this.langEn = langEn;
    }

    public int getLangCn() {
        return langCn;
    }

    public void setLangCn(int langCn) {
        this.langCn = langCn;
    }

    public int getLangJp() {
        return langJp;
    }

    public void setLangJp(int langJp) {
        this.langJp = langJp;
    }

    public String getLangEtc() {
        return langEtc;
    }

    public void setLangEtc(String langEtc) {
        this.langEtc = langEtc;
    }

    @Override
    public String toString() {
        return "AgencyVO{" +
                "agncNmKr='" + agncNmKr + '\'' +
                ", agncNmEn='" + agncNmEn + '\'' +
                ", ownKr='" + ownKr + '\'' +
                ", ownEn='" + ownEn + '\'' +
                ", phone='" + phone + '\'' +
                ", fax='" + fax + '\'' +
                ", adrGuKr='" + adrGuKr + '\'' +
                ", adrDtKr='" + adrDtKr + '\'' +
                ", adrGuEn='" + adrGuEn + '\'' +
                ", adrDtEn='" + adrDtEn + '\'' +
                ", langEn=" + langEn +
                ", langCn=" + langCn +
                ", langJp=" + langJp +
                ", langEtc='" + langEtc + '\'' +
                '}';
    }
}
