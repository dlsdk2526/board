package org.ina.time;

import org.ina.common.config.RootConfig;
import org.ina.time.config.TimeConfig;
import org.ina.time.mapper.TimeMapper;
import org.ina.time.service.TimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class,TimeConfig.class}) //스프링이 실행되면서 어떤 설정 정보를 읽어 들여야 하는지 명시한다
@Log4j
public class TimeTests {
	
	@Autowired
	private TimeMapper timeMapper;
	@Autowired
	private TimeService timeService;
	
	//timeMapper test
	@Test
	public void timeMapper() {
		log.info("time mapper test.........");
		log.info(timeMapper.getTime());
	}

	//time xml test
	@Test
	public void timeMapper2() {
		log.info("time mapper test.........");
		log.info(timeMapper.getTime2());
	}
	
	//time service test
	@Test
	public void timeService() {
		log.info("time service test........");
		log.info(timeService.getTime());
	}
}









