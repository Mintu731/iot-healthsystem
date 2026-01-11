package com.healthcare.dto;

public class HealthLogResponseDto {

    private Long id;
    private Long entryId;
    private Double bpm;
    private Double avgBpm;
    private Double temperature;
    private Double humidity;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getEntryId() {
		return entryId;
	}
	public void setEntryId(Long entryId) {
		this.entryId = entryId;
	}
	public Double getBpm() {
		return bpm;
	}
	public void setBpm(Double bpm) {
		this.bpm = bpm;
	}
	public Double getAvgBpm() {
		return avgBpm;
	}
	public void setAvgBpm(Double avgBpm) {
		this.avgBpm = avgBpm;
	}
	public Double getTemperature() {
		return temperature;
	}
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}
	public Double getHumidity() {
		return humidity;
	}
	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

    // getters & setters
}
