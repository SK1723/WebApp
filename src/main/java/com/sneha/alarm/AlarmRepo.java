package com.sneha.alarm;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface AlarmRepo extends CrudRepository<Alarm, Long> {
	List<Alarm> findByAlarmName(String alarmName);

	List<Alarm> findAll(Sort sortByNumberOfVotesDesc);
	
}
