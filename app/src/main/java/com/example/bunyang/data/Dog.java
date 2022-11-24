package com.example.bunyang.data;

import java.io.Serializable;

public class Dog implements Serializable {
    private int id;
    private String spin1; // 경력
    private String spin2; // 지역
    private String spin3; // 성별
    private String spin4; // 나이
    private String spin5; // 견종
    private String spin6; // 크기
    private String content; // 설명
    private String imageUrl;


    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpin1() {
        return spin1;
    }

    public void setSpin1(String spin1) {this.spin1 = spin1; }

    public String getSpin2() {
        return spin2;
    }

    public void setSpin2(String spin2) {
        this.spin2 = spin2;
    }

    public String getSpin3() {
        return spin3;
    }

    public void setSpin3(String spin3) {
        this.spin3 = spin3;
    }

    public String getSpin4() {
        return spin4;
    }

    public void setSpin4(String spin4) {
        this.spin4 = spin4;
    }

    public String getSpin5() {
        return spin5;
    }

    public void setSpin5(String spin5) {
        this.spin5 = spin5;
    }

    public String getSpin6() {
        return spin6;
    }

    public void setSpin6(String spin6) {
        this.spin6 = spin6;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

