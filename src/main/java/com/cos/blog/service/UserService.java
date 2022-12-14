package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다.
@Service
public class UserService {

	// 1. 트랜잭션 관리
	// 2. 서비스 의미 때문

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public void 회원가입(User user) {
		userRepository.save(user);
	}
}
