package com.xy.demo.model;


//红外 射频
public class IrModel {

    public int[] irCodeList;

    public int frequency;

    public int[] getIrCodeList() {
        return irCodeList;
    }

    public void setIrCodeList(int[] irCodeList) {
        this.irCodeList = irCodeList;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
