package org.ina.common.config;

import javax.sql.DataSource;

import org.ina.board.security.CustomAccessDeniedHandler;
import org.ina.board.security.CustomLoginSuccessHandler;
import org.ina.board.security.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import lombok.extern.log4j.Log4j;

@Configuration
@EnableWebSecurity //스프링 mvc와 스프링 시큐리티를 결합 
@Log4j
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private DataSource dataSource;

	//	@Override
	//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	//		
	//		log.info("configure..............");
	//		auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("ADMIN","MEMBER");
	//		auth.inMemoryAuthentication().withUser("member").password("$2a$10$z4TX7Fqrhk2.7CYjh0.MYO64pBanu07u2yJWGsZsujCoSJhtRZSpu").roles("MEMBER");
	//	}


	@Bean
	public AuthenticationSuccessHandler loginSuccessHandler() {
		return new CustomLoginSuccessHandler();
	}


	@Bean 
	public AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService customUserDetailService() {
		return new CustomUserDetailService();
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		return repo;
	}

	//	@Override
	//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	//		
	//		log.info("configure JDBC................");
	//		
	//		String queryUser = "select userid, userpw, enabled from tbl_member where userid = ?";
	//		String queryDetails = "select userid, auth from tbl_member_auth where userid = ?";
	//			
	//		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
	//		.usersByUsernameQuery(queryUser)
	//		.authoritiesByUsernameQuery(queryDetails);
	//		
	//	}

	
	// in custom userdetails
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(customUserDetailService()).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
		.antMatchers("/sample/all").permitAll()
		.antMatchers("/sample/admin").access("hasRole('ROLE_ADMIN' )")
		.antMatchers("/sample/member").access("hasRole('ROLE_MEMBER')");

		//로그인
		http.formLogin().loginPage("/customLogin").loginProcessingUrl("/login");

		//접근제한처리
		http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());

		//로그아웃
		http.logout().logoutUrl("/customLogout").invalidateHttpSession(true).deleteCookies("remember-me","JSESSION_ID");

		//csrf토큰생성 비활성화하기 
		http.csrf().disable();
		
		//rememberme
		http.rememberMe().key("zerock").tokenRepository(persistentTokenRepository()).tokenValiditySeconds(604800);
	}





}
