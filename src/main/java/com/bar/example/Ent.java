package com.bar.example;

import org.springframework.data.annotation.Id;

public class Ent {

    @Id
    private Integer s;
    private String f;

    public Ent(String f, Integer s) {
        this.f = f;
        this.s = s;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public Integer getS() {
        return s;
    }

    public void setS(Integer s) {
        this.s = s;
    }
}
