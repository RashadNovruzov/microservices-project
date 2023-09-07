package com.microservices.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.microservices.productservice.dto.request.ProductRequestDto;
import com.microservices.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Container
	public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:15.4")
			.withDatabaseName("test")
			.withUsername("postgres")
			.withPassword("root");


	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductRepository productRepository;

	@DynamicPropertySource
	static void postgresProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
		registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
		registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
	}

	@Test
	void shouldCreateProduct() {
		try {
			for (int i = 0;i<10;i++){
				mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(getRequestBody())))
						.andExpect(status().isCreated());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		Assertions.assertEquals(10, productRepository.findAll().size());
	}

	@Test
	void shouldGetAllProducts(){
		Assertions.assertEquals(10,productRepository.findAll().size());
	}

	private ProductRequestDto getRequestBody() {
		return ProductRequestDto.builder()
				.name("Helo")
				.description("sidn")
				.price(123.11)
				.build();
	}

}
