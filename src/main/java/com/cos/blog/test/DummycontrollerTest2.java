package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//html파일이 아니라 data를 리턴해주는 controller = RestController
@RestController
public class DummycontrollerTest2 {

	@Autowired
	private UserRepository userRepository;

	// save함수는 id를 전달하지 않으면 insert를 해주고
	// save함수는 id를 전달하면 해당id에 대한 데이터가 있으면 update를 해주고
	// save함수는 id를 전달하면 해당id에 대한 데이터가 없으면 insert를 해요.
	// email.password

	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (Exception e) {
			return "삭제에 실패하였습니다. 해당id는 DB에 없습니다.";
		}

		return "삭제되었습니다. id:" + id;

	}

	@Transactional // 함수 종료시에 자동 commit 됨.
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { // @RequestBody json 데이터를 요청 =>Java
																					// Object(MessageConverter의
																					// Jackson라이브러리가 변환해서 받아줘요.

		System.out.println("id: " + id);
		System.out.println("password: " + requestUser.getPassword());
		System.out.println("email: " + requestUser.getEmail());

		User user = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});

		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());

		// userRepository.save(user);

		// 더티 체킹
		return user;
	}

	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
	}

	// 한페이지당 2건에 데이터를 리턴받아 볼 예정
	@GetMapping("/dummy/user")
	public List<User> pageList(
			@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<User> pagingUser = userRepository.findAll(pageable);

		if (pagingUser.isLast()) {

		}

		List<User> users = pagingUser.getContent();
		return users;
	}

	// {id} 주소로 파라메터를 전달받을수 있음.
	// http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {

		// user/4을 찾으면 내가 DB에서 못찾아오게되면 user가 null이 될것아냐
		// 그럼 return null 이 리턴이 되자나... 그럼 프로글매에 문제
		// optional 로 너의 user 객체를 감싸서 가져올테니 null 인지 아닌지 판단해서 return해
		// 람다식
//		User user = userRepository.findById(id).orElseThrow(()->{
//			return new IllegalArgumentException("해당 유저는 없습니다.id : " + id);
//		});

		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다.id : " + id);
			}
		});

		// 요청 : 웹브라우저
		// user 객체 = 자바 오브젝트
		// 변환(웹브라우저가 이해할수 있는 데이터) ->json(gson 라이브러리)
		// 스부링부트 = MessageConverter라는 애가 응답시에 자동 작동
		// 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 jackson 라이브러리를 호출해서
		// user 오브젝트를 json으로 변환해서 브라우저에게 던져줍니다.
		return user;

	}

	// http://localhost:8000/blog/dummy/join(요청)
	// http의 body 에 username, password, email데이터를 가지고(요청)
	@PostMapping("/dummy/join")
	public String join(User user) {

		System.out.println("id : " + user.getId());
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());
		System.out.println("role : " + user.getRole());
		System.out.println("createDate : " + user.getCreateDate());/// ㄴㄴㄹㄻㄴㄻㄹㄴㅁㄴㄻㄴㄻㄹㄴ

		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";

	}
}
