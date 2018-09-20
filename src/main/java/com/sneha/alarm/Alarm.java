package com.sneha.alarm;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Alarm {
	public Alarm() {		
		this.numberOfVotes = 0;
		this.alarmDate = new Timestamp(System.currentTimeMillis());
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long alarmId;
	
	private String alarmName;
	private Integer numberOfVotes;
	private Timestamp alarmDate;
	

	
	public Long getAlarmId() {
		return alarmId;
	}
	public void setAlarmId(Long alarmId) {
		this.alarmId = alarmId;
	}
	public String getAlarmName() {
		return alarmName;
	}	
	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName.toUpperCase();
	}
	public Integer getNumberOfVotes() {
		return numberOfVotes;
	}
	public void setNumberOfVotes(Integer numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}
	public Timestamp getAlarmDate() {
		return alarmDate;
	}
	public void setAlarmDate(Timestamp alarmDate) {
		this.alarmDate = alarmDate;
	}
	

}
