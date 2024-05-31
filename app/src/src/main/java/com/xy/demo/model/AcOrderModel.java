package com.xy.demo.model;

public class AcOrderModel {


    public boolean isOpen = false;
    //温度
    public int tcInt = 26;

    public int speedInt = 0;


    public int modeInt = 0;

    public int swingInt = 0;


    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getTcInt() {
        return tcInt;
    }

    public void setTcInt(int tcInt) {
        this.tcInt = tcInt;
    }

    public int getSpeedInt() {
        return speedInt;
    }

    public void setSpeedInt(int speedInt) {
        this.speedInt = speedInt;
    }

    public int getModeInt() {
        return modeInt;
    }

    public void setModeInt(int modeInt) {
        this.modeInt = modeInt;
    }

    public int getSwingInt() {
        return swingInt;
    }

    public void setSwingInt(int swingInt) {
        this.swingInt = swingInt;
    }


    @Override
    public String toString() {
        return "{" +
                "isOpen=" + isOpen +
                ", tcInt=" + tcInt +
                ", speedInt=" + speedInt +
                ", modeInt=" + modeInt +
                ", swingInt=" + swingInt +
                '}';
    }
}
