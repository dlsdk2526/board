package org.ina.board.security;

import org.ina.board.domain.CustomUser;
import org.ina.board.domain.MemberVO;
import org.ina.board.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	MemberMapper memberMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.warn("load user by username: "+ username);
		
		MemberVO vo = memberMapper.read(username);
		
		log.warn("queried by memeber mapper:" + vo );
		
		return vo == null? null: new CustomUser(vo);
	}
}
