package com.app.carrental;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.app.carrental.controller.WebsiteController;
import com.app.carrental.controller.rest.CarController;
//import com.app.carrental.controller.rest.RatingController;
//import com.app.carrental.controller.rest.UserController;
import com.app.carrental.repository.CarRepository;
//import com.app.carrental.repository.RatingRepository;
//import com.app.carrental.repository.UserRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CarRentalApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebsiteController websiteController;
	@Autowired
	private CarController carController;
//	@Autowired
//	private RatingController ratingController;
//	@Autowired
//	private UserController userController;
	@Autowired
	private CarRepository carRepository;
//	@Autowired
//	private UserRepository userRepository;
//	@Autowired
//	private RatingRepository ratingRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void controllerHealthTest(){
		assertThat(websiteController).isNotNull();
		assertThat(carController).isNotNull();
//		assertThat(userController).isNotNull();
//		assertThat(ratingController).isNotNull();
	}

	@Test
	void repositoryHealthTest(){
		assertThat(carRepository).isNotNull();
//		assertThat(userRepository).isNotNull();
//		assertThat(ratingRepository).isNotNull();
	}

	@Test
	void hiCheck() throws Exception {
		this.mockMvc.perform(get("/hi"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Hi!")));
	}

}
