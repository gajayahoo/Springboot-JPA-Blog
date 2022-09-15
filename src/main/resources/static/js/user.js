let index = {

	init: function() {
		$("#btn-save").on("click", () => { this.save(); });  //functon() , 사용하지않는 이유는 this를 바인딩하기위해서!!
	},

	save: function() {
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};

		/*
		1. 요청에 대한 응답을 html이 아닌 data(json)를 받기 위하여. 앱과 웹이 하나의 서버를 통해서 개발하기위해
		2. 비동기 통신을 하기 위해서이다.
		
		 */
		//ajax 통신을 이용해서 3개의 파라미터를 json으로 변경하여 insert 요청!!
		//ajax 가 통신을 성공하고 서버가json을 리턴해주면 자동으로 자바 오브젝트로 변환해주네요.
		$.ajax({
			type: "POST",
			url: "/blog/api/user",
			data: JSON.stringify(data),//http body데이터
			contentType: "application/json;charset=utf-8",//body데이터가 어떤 타입인지(MIME)
			dataType: "json" //요청을 서버로해서 응답이 왔을때 기본적으로 모든것이 문자열(생긴게 json이라면) =>javascript오브젝트로 변경
		}).done(function(resp) {
			alert("회원가입이 완료되었습니다.");
			location.href = "/blog";
		}).fail(function() {
			alsert(JSON.stringify(error));
		});
	}
}

index.init();
