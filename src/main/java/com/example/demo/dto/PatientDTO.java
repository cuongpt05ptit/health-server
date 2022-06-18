package com.example.demo.dto;

public class PatientDTO {
    private String gender;
    private double age;
    private double urea;
    private double cr;
    private double hbA1c;
    private double chol;
    private double tg;
    private double hdl;
    private double ldl;
    private double vldl;
    private double bmi;

    public PatientDTO() {
    }

    public PatientDTO(String gender, double age, double urea, double cr, double hbA1c, double chol, double tg, double hdl, double ldl, double vldl, double bmi) {
        this.gender = gender;
        this.age = age;
        this.urea = urea;
        this.cr = cr;
        this.hbA1c = hbA1c;
        this.chol = chol;
        this.tg = tg;
        this.hdl = hdl;
        this.ldl = ldl;
        this.vldl = vldl;
        this.bmi = bmi;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public double getUrea() {
        return urea;
    }

    public void setUrea(double urea) {
        this.urea = urea;
    }

    public double getCr() {
        return cr;
    }

    public void setCr(double cr) {
        this.cr = cr;
    }

    public double getHbA1c() {
        return hbA1c;
    }

    public void setHbA1c(double hbA1c) {
        this.hbA1c = hbA1c;
    }

    public double getChol() {
        return chol;
    }

    public void setChol(double chol) {
        this.chol = chol;
    }

    public double getTg() {
        return tg;
    }

    public void setTg(double tg) {
        this.tg = tg;
    }

    public double getHdl() {
        return hdl;
    }

    public void setHdl(double hdl) {
        this.hdl = hdl;
    }

    public double getLdl() {
        return ldl;
    }

    public void setLdl(double ldl) {
        this.ldl = ldl;
    }

    public double getVldl() {
        return vldl;
    }

    public void setVldl(double vldl) {
        this.vldl = vldl;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }
}


