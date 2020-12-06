package com.cvindexer.cvindexer;

import com.cvindexer.cvindexer.models.cv;
import com.cvindexer.cvindexer.resources.CVResource;
import com.cvindexer.cvindexer.services.CVServices;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {CvindexerApplication.class})
class CvResourceTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private CVServices cvServicesMock;

	@Test
	public void getAll() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		when(cvServicesMock.getAllCV()).thenReturn(List.of());
		this.mockMvc.perform(get("/v1/api/cvindexer/allCV")).andExpect(status().isOk());
	}

	@Test
	public void research() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		String research = "Lille;Java";
		when(cvServicesMock.getResearch(research)).thenReturn(List.of());
		this.mockMvc.perform(get("/v1/api/cvindexer/research").param("research", research)).andExpect(status().isOk());
	}


	/* ne fonctionne pas encore

	@Test
	public void insertFileDoxs() throws Exception {

		this.mockMvc = webAppContextSetup(webApplicationContext).build();

		when(cvServicesMock.insertCv(any(cv.class))).thenReturn(new cv());
		FileInputStream fis = new FileInputStream("cv-ingenieur-informatique.docx");
		MockMultipartFile multipartFile = new MockMultipartFile("file", fis);


		mockMvc.perform(fileUpload("/v1/api/cvindexer/upload").file(multipartFile))
				.andDo(print())
				.andExpect(status().is(201));
	}
	*/

}
