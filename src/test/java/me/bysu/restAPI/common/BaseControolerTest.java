package me.bysu.restAPI.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
// SpringBootTest is good when web test
// mocking is very hard to make test
// MOCK MVC MOcKING dispatcher servlet

@AutoConfigureRestDocs // for using restDocs

@Import(RestDocsConfiguration.class)

@ActiveProfiles("test") // 중복 설정 삭제

@Ignore // this class do not have test so Ignore
public class BaseControolerTest {

    @Autowired
    protected MockMvc mockMvc;


    @Autowired // objectMapper auto set bean
    protected ObjectMapper objectMapper;

    @Autowired
    protected ModelMapper modelMapper;

}
