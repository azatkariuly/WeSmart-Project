package com.example.wesmart.stomp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Telemetry {
    @Expose
    @SerializedName(value = "sensorId")
    private Long sensorId;

    @Expose
    @SerializedName(value = "current")
    private Double current;

    @Expose
    @SerializedName(value = "voltage")
    private Double voltage;

    @Expose
    @SerializedName(value = "cosphi")
    private Double cosphi;

    @Expose
    @SerializedName(value = "power")
    private Double power;

    @Expose
    @SerializedName(value = "ageingRate")
    private Double ageingRate;

    @Expose
    @SerializedName(value = "temperature")
    private Double temperature;

    @Expose
    @SerializedName(value = "vibration")
    private Double vibration;

    @Expose
    @SerializedName(value = "measuredAt")
    private Long measuredAt;

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public Double getCurrent() {
        return current;
    }

    public void setCurrent(Double current) {
        this.current = current;
    }

    public Double getVoltage() {
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }

    public Double getCosphi() {
        return cosphi;
    }

    public void setCosphi(Double cosphi) {
        this.cosphi = cosphi;
    }

    public Double getPower() {
        return power;
    }

    public void setPower(Double power) {
        this.power = power;
    }

    public Double getAgeingRate() {
        return ageingRate;
    }

    public void setAgeingRate(Double ageingRate) {
        this.ageingRate = ageingRate;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getVibration() {
        return vibration;
    }

    public void setVibration(Double vibration) {
        this.vibration = vibration;
    }

    public Long getMeasuredAt() {
        return measuredAt;
    }

    public void setMeasuredAt(Long measuredAt) {
        this.measuredAt = measuredAt;
    }
}
