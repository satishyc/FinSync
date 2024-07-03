package com.finSync.entity.response;



public class MutualFund {
    private Double totalInvested;
    private Double currentValue;
    private Double gain;

    public MutualFund(Double totalInvested, Double currentValue, Double gain) {
        this.totalInvested = totalInvested;
        this.currentValue = currentValue;
        this.gain = gain;
    }

    public Double getTotalInvested() {
        return totalInvested;
    }

    public void setTotalInvested(Double totalInvested) {
        this.totalInvested = totalInvested;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }

    public Double getGain() {
        return gain;
    }

    public void setGain(Double gain) {
        this.gain = gain;
    }


}
