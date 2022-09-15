package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {

	// http://localhost:8000/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		// 파일리턴 기본경로 : src/main/recources/static
		// 리터명 :/home.html
		// 풀경로 : src/main/recources/static/home.html
		return "/home.html";
	}

	@GetMapping("/temp/jsp2")
	public String tempJsp2() {
		return "test.jsp";
	}
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		// prefix : /WEB-INF/views/
		// suffix : .jsp
		// 풀경로 : /WEB-INF/views/test.jsp
		return "test";
	}
}
