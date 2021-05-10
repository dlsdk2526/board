package org.ina.board.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import lombok.extern.log4j.Log4j;

@Configuration
@Log4j
@MapperScan(basePackages = {"org.ina.board.mapper"})
@ComponentScan(basePackages = {"org.ina.board.service"})
public class BoardConfig {

}
