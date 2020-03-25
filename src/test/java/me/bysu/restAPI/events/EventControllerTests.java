package me.bysu.restAPI.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.bysu.restAPI.common.TestDescription;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
// SpringBootTest is good when web test
// mocking is very hard to make test
// MOCK MVC MOcKING dispatcher servlet
public class EventControllerTests {

    @Autowired
    MockMvc mockMvc;


    @Autowired // objectMapper auto set bean
    ObjectMapper objectMapper;

    /*
    @MockBean // find why use mockBean
    EventRepository eventRepository;
    */

    @Test
    @TestDescription("normal create event test")
    public void createEvent() throws Exception {

        EventDto eventDto = EventDto.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2020,3,23,22,23))
                .closeEnrollmentDateTime(LocalDateTime.of(2020,3,24,22,23))
                .beginEventDateTime(LocalDateTime.of(2020,3,25,22,23))
                .endEventDateTime(LocalDateTime.of(2020,3,26,22,23))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("A-plus")
                .build();
                // whatever you set, It is changed appropriately through dto
//        Mockito.when(eventRepository.save(event)).thenReturn(event); : this event is not that event
        mockMvc.perform(post("/api/events/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(objectMapper.writeValueAsString(eventDto))
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("id").value(Matchers.not(100)))
                .andExpect(jsonPath("free").value(Matchers.not(true)));
    }

    @Test
    @TestDescription(" use cant not input values create error event test")
    public void createEvent_BadRequest() throws Exception {

        Event event = Event.builder()
                .id(100)
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2020,3,23,22,23))
                .closeEnrollmentDateTime(LocalDateTime.of(2020,3,24,22,23))
                .beginEventDateTime(LocalDateTime.of(2020,3,25,22,23))
                .endEventDateTime(LocalDateTime.of(2020,3,26,22,23))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("A-plus")
                .free(true)
                .offline(false)
                .eventStatus(EventStatus.PUBLISHED)
                .build();
        // whatever you set, It is changed appropriately through dto
//        Mockito.when(eventRepository.save(event)).thenReturn(event); : this event is not that event
        mockMvc.perform(post("/api/events/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(objectMapper.writeValueAsString(event))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createEvent_BadRequest_Empty_Input() throws Exception {
        EventDto eventDto = EventDto.builder().build();

        this.mockMvc.perform(post("/api/events/")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(this.objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isBadRequest());
    }


    @Test
    @TestDescription("some values do not enter but they need to enter")
    public void createEvent_BadRequest_WrongInput() throws Exception {

        EventDto eventDto = EventDto.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2020, 3, 27, 22, 23))
                .closeEnrollmentDateTime(LocalDateTime.of(2020, 3, 24, 22, 23))
                .beginEventDateTime(LocalDateTime.of(2020, 3, 25, 22, 23))
                .endEventDateTime(LocalDateTime.of(2020, 3, 21, 22, 23))
                .basePrice(10000)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("A-plus")
                .build();

        this.mockMvc.perform(post("/api/events/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].objectName").exists())
                .andExpect(jsonPath("$[0].defaultMessage").exists())
                .andExpect(jsonPath("$[0].code").exists());
    }
}
