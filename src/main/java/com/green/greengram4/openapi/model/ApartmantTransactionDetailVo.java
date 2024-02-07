package com.green.greengram4.openapi.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;


public class ApartmantTransactionDetailVo {
    private int dealAmount;
    private String buildYear;
    private String dealYear;
    private String dealMonth;
    private String dealDay;
    private String dong;
    private String apartmentName;
    private float areaForExclusiveUse;
    private String jibun;
    private int foor;

    @JsonSetter("거래금액")
    public void setDealAmount(String dealAmount){
        this.dealAmount = Integer.parseInt(dealAmount.replaceAll(",","").trim());
        // trim():앞뒤에 공백 날리기,
        // *dealAmount.replaceAll(",", ""): dealAmount 문자열에서 모든 쉼표(,)를 빈 문자열로 대체합니다.
        // Integer.parseInt(...): 최종적으로 문자열을 정수로 변환합니다.
    }
    @JsonSetter("dealAmount")
    public int getDealAmount() {
        return dealAmount;
    }
    @JsonSetter("buildYear")
    public String getBuildYear() {
        return buildYear;
    }
    @JsonSetter("건축년도")
    public void setBuildyear(String buildYear) {
        this.buildYear = buildYear;
    }
    @JsonSetter("dealYear")
    public String getDealyear() {
        return dealYear;
    }
    @JsonSetter("년")
    public void setDealyear(String dealYear) {
        this.dealYear = dealYear;
    }
    @JsonSetter("dealMonth")
    public String getDealMonth() {
        return dealMonth;
    }
    @JsonSetter("월")
    public void setDealmonth(String dealMonth) {
        this.dealMonth = dealMonth;
    }
    @JsonSetter("dealDay")
    public String getDealday() {
        return dealDay;
    }
    @JsonSetter("일")
    public void setDealday(String dealDay) {
        this.dealDay = dealDay;
    }
    @JsonSetter("dong")
    public String getDong() {
        return dong;
    }
    @JsonSetter("법정동")
    public void setDong(String dong) {
        this.dong = dong;
    }
    @JsonSetter("apartmentName")
    public String getApartmentName() {
        return apartmentName;
    }
    @JsonSetter("아파트")
    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }
    @JsonSetter("areaForExclusiveUse")
    public float getAreaForExclusiveUse() {
        return areaForExclusiveUse;
    }
    @JsonSetter("전용면적")
    public void setAreaForExclusiveUse(float areaForExclusiveUse) {
        this.areaForExclusiveUse = areaForExclusiveUse;
    }
    @JsonSetter("jibun")
    public String getJibun() {
        return jibun;
    }
    @JsonSetter("지번")
    public void setJibun(String jibun) {
        this.jibun = jibun;
    }
    @JsonSetter("foor")
    public int getFoor() {
        return foor;
    }
    @JsonSetter("층")
    public void setFoor(int foor) {
        this.foor = foor;
    }
}
