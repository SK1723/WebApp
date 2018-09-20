package com.sneha.alarm;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import org.json.simple.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AlarmController {	
//    @Value("${spring.application.name}")
//    String appName;
    
    @Autowired
	AlarmRepo alarmRepo;
    
    private Sort sortByNumberOfVotesDesc() {
        return new Sort(Sort.Direction.DESC, "numberOfVotes", "alarmDate");
    }
 
    @GetMapping("/")
    public String homePage(Model model) {
    	model.addAttribute("alarm", new Alarm());
        return "alarms";
    }
    
    @GetMapping("/alarms")
    public String allAlarms(Model model) {
    	Alarm alarm = new Alarm();
		model.addAttribute("alarm", alarm);
    	model.addAttribute("alarms", alarmRepo.findAll(sortByNumberOfVotesDesc()));
        return "alarms";
    }
    
    @RequestMapping(value="/upVote", method=RequestMethod.POST)
    public String saveUpvote(@ModelAttribute("alarmId") Long alarmId, Model model) {
    	
    	Optional<Alarm> optAlarm = alarmRepo.findById(alarmId);
    	Alarm alarm = optAlarm.get();
    	alarm.setNumberOfVotes(alarm.getNumberOfVotes()+1);
    	alarmRepo.save(alarm);
    	return allAlarms(model);
    }
    
    @RequestMapping(value="/alarms", method=RequestMethod.POST)
    public String saveAlarm(@ModelAttribute("alarm") Alarm alarm, Model model) throws IOException  {
    	List<Alarm> alarms = alarmRepo.findByAlarmName(alarm.getAlarmName());
    	if(alarms.isEmpty()) {
    		alarm.setNumberOfVotes(0);
    		model.addAttribute("alarm", alarmRepo.save(alarm));
    		postAlarmToHandshake(alarm);
    	}else {
    		Alarm exisitingAlarm = alarms.get(0);
    		alarm.setAlarmId(exisitingAlarm.getAlarmId());
    		model.addAttribute("alarm", alarmRepo.save(alarm));
    	}
            
    	model.addAttribute("alarms", alarmRepo.findAll(sortByNumberOfVotesDesc()));
    	return "alarms";
    }

	private void postAlarmToHandshake(Alarm alarm) throws IOException {
		JSONObject param = new JSONObject();
		param.put("alarm_id", alarm.getAlarmId().toString());
		param.put("time stamp", alarm.getAlarmDate().toString());
		param.put("alarm name", alarm.getAlarmName());
		param.put("number of votes", alarm.getNumberOfVotes().toString());
		
		URL url = new URL("https://bellbird.joinhandshake-internal.com/push");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setDoOutput(true);
		OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream());
		output.write(param.toString());
		
		output.flush();
		output.close();

		
	}
}
