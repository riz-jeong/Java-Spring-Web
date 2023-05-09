package com.spring.ch2;

import java.io.FileNotFoundException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//@ResponseStatus(HttpStatus.BAD_REQUEST) // 상태코드 500 -> 400
class MyException extends RuntimeException {
	MyException(String msg) {
		super(msg);
	}
	MyException() { this(""); }
}

@Controller
public class ExeptionController2 {
	
	@RequestMapping("/ex4")
	public String main() throws Exception {
		throw new MyException("예외가 발생했습니다.");
	}
	
	@RequestMapping("/ex5")
	public String main2() throws Exception {
		throw new NullPointerException("예외가 발생했습니다.");
	}
	
	@RequestMapping("/ex6")
	public String main3() throws Exception {
		throw new FileNotFoundException("예외가 발생했습니다.");
	}
}
