package org.ina.time.service;

import org.ina.time.mapper.TimeMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@RequiredArgsConstructor
public class TimeServiceImpl implements TimeService {

	private final TimeMapper timeMapper;
	
	@Override
	public String getTime() {
		
		log.info("timemapper......");
		
		return timeMapper.getTime();
		
	}

}
