package com.ilia.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilia.schedule.api.resources.TimeSheetCreateModel;
import com.ilia.schedule.repositories.TimeSheetRepository;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class IliaScheduleApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TimeSheetRepository timeSheetRepository;

	@AfterEach
	void teardown() {
		timeSheetRepository.deleteAll();
	}

	@Test
	void registrationWorksThroughAllLayers() throws Exception {

		var checkedTime = LocalDateTime.parse("2018-08-22T00:00");
		var request = new TimeSheetCreateModel();
		request.setCheckedDateTime(checkedTime);

		mockMvc.perform(MockMvcRequestBuilders
						.post("/api/v1/batidas")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request))
				)
				.andDo(print())
				.andExpect(status().is2xxSuccessful());

		assertThat(timeSheetRepository.findFirstByCheckedDateTime(checkedTime).isPresent()).isTrue();

	}

}


