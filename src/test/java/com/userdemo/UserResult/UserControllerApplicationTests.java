package com.userdemo.UserResult;

import com.userdemo.UserResult.rest.UserController;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserControllerApplicationTests {

	@Autowired
	private UserController userController;

	@Test
	void contextLoads() throws Exception {
		assertThat(userController).isNotNull();
	}
}
