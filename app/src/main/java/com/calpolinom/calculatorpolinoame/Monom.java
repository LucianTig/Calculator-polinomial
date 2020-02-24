package com.calpolinom.calculatorpolinoame;


public class Monom {
    private double argument;
    private int putere;

    public Monom(double argument, int putere) {
        this.argument=argument;
        this.putere=putere;
    }

    public double getArg() {
        return argument;
    }

    public int getPutere() {
        return putere;
    }

    public void setArg(double a) {
        this.argument=a;
    }

    public void setPutere(int a) {
        this.putere=a;
    }

}

