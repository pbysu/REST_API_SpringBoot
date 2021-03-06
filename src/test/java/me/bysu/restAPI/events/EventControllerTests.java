package me.bysu.restAPI.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.bysu.restAPI.common.BaseControolerTest;
import me.bysu.restAPI.common.RestDocsConfiguration;
import me.bysu.restAPI.common.TestDescription;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.print.attribute.standard.Media;
import java.time.LocalDateTime;
import java.util.stream.IntStream;


import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// inherit make It do not write annotation in here
/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
// SpringBootTest is good when web test
// mocking is very hard to make test
// MOCK MVC MOcKING dispatcher servlet

@AutoConfigureRestDocs // for using restDocs

@Import(RestDocsConfiguration.class)

@ActiveProfiles("test") // 중복 설정 삭제*/
public class EventControllerTests extends BaseControolerTest {

    // move to parent class
/*    @Autowired
    MockMvc mockMvc;


    @Autowired // objectMapper auto set bean
    ObjectMapper objectMapper;

    @Autowired
    ModelMapper modelMapper;*/

    @Autowired
    EventRepository eventRepository;

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
                .beginEnrollmentDateTime(LocalDateTime.of(2020, 3, 23, 22, 23))
                .closeEnrollmentDateTime(LocalDateTime.of(2020, 3, 24, 22, 23))
                .beginEventDateTime(LocalDateTime.of(2020, 3, 25, 22, 23))
                .endEventDateTime(LocalDateTime.of(2020, 3, 26, 22, 23))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("A-plus")
                .build();
        // whatever you set, It is changed appropriately through dto
//        Mockito.when(eventRepository.save(event)).thenReturn(event); : this event is not that event
        mockMvc.perform(post("/api/events")
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
                .andExpect(jsonPath("free").value(Matchers.not(true)))
                .andDo(document("create-event",
                        links(
                                linkWithRel("self").description("link to self"),
                                linkWithRel("query-events").description("link to query"),
                                linkWithRel("update-events").description("link to update existing event"),
                                linkWithRel("profile").description("link to update an existion")

                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("Accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                        ),
                        requestFields(
                                fieldWithPath("name").description("Name of new event"),
                                fieldWithPath("description").description("description of new event"),
                                fieldWithPath("beginEnrollmentDateTime").description("begin enroll time"),
                                fieldWithPath("closeEnrollmentDateTime").description("close enroll time"),
                                fieldWithPath("beginEventDateTime").description("begin event time"),
                                fieldWithPath("endEventDateTime").description("end event time"),
                                fieldWithPath("location").description("location of new event"),
                                fieldWithPath("basePrice").description("base price of new event"),
                                fieldWithPath("maxPrice").description("max price of new event"),
                                fieldWithPath("limitOfEnrollment").description("limit of enrollment")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location in header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type in header")
                        ),
                        // it added relaxed : need part of value in doc
                        // ResponseFields : need all value in doc
                        responseFields(
                                fieldWithPath("id").description("Identifier of new event"),
                                fieldWithPath("name").description("Name of new event"),
                                fieldWithPath("description").description("description of new event"),
                                fieldWithPath("beginEnrollmentDateTime").description("begin enroll time"),
                                fieldWithPath("closeEnrollmentDateTime").description("close enroll time"),
                                fieldWithPath("beginEventDateTime").description("begin event time"),
                                fieldWithPath("endEventDateTime").description("end event time"),
                                fieldWithPath("location").description("location of new event"),
                                fieldWithPath("basePrice").description("base price of new event"),
                                fieldWithPath("maxPrice").description("max price of new event"),
                                fieldWithPath("limitOfEnrollment").description("limit of enrollment"),
                                fieldWithPath("free").description("it tells if this event is free or not"),
                                fieldWithPath("offline").description("it tells if this event is offline or not"),
                                fieldWithPath("eventStatus").description("event status"),
                                fieldWithPath("_links.query-events.href").description("link to query event list"),
                                fieldWithPath("_links.update-events.href").description("link to update existing event"),
                                fieldWithPath("_links.profile.href").description("link to profile"),
                                fieldWithPath("_links.self.href").description("link to self")
                        )

                ))

        ;
    }

    @Test
    @TestDescription(" use cant not input values create error event test")
    public void createEvent_BadRequest() throws Exception {

        Event event = Event.builder()
                .id(100)
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2020, 3, 23, 22, 23))
                .closeEnrollmentDateTime(LocalDateTime.of(2020, 3, 24, 22, 23))
                .beginEventDateTime(LocalDateTime.of(2020, 3, 25, 22, 23))
                .endEventDateTime(LocalDateTime.of(2020, 3, 26, 22, 23))
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
                .andExpect(jsonPath("content[0].objectName").exists())
                .andExpect(jsonPath("content[0].defaultMessage").exists())
                .andExpect(jsonPath("content[0].code").exists())
                .andExpect(jsonPath("_links.index").exists())

        ;
    }

    private Event generateEvent(int idx) {
        Event event = Event.builder()
                .name("event " + idx)
                .description("test event")
                .beginEnrollmentDateTime(LocalDateTime.of(2020, 3, 23, 22, 23))
                .closeEnrollmentDateTime(LocalDateTime.of(2020, 3, 24, 22, 23))
                .beginEventDateTime(LocalDateTime.of(2020, 3, 25, 22, 23))
                .endEventDateTime(LocalDateTime.of(2020, 3, 26, 22, 23))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("A-plus")
                .free(false)
                .offline(true)
                .eventStatus(EventStatus.DRAFT)
                .build();
        return this.eventRepository.save(event);
    }

    @Test
    @TestDescription("Search 30 pages of 10 events twice")
    public void queryEvents() throws Exception {

        // Given
        IntStream.range(0, 30).forEach(this::generateEvent);

        // When
        this.mockMvc.perform(get("/api/events")
                .param("page", "1") // 0,1  so 1
                .param("size", "10")
                .param("sort", "name,DESC")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-events"))

        ;
    }






    @Test
    @TestDescription("Searching for an existing event")
    public void getEvent() throws Exception {

        // Given
        Event event = this.generateEvent(100);

        // When & Then
        this.mockMvc.perform(get("/api/events/{id}", event.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-an-event"))
        ;
    }

    @Test
    @TestDescription("take 404 when it is not exist")
    public void getEvent404() throws Exception {
        // When & Then
        this.mockMvc.perform(get("/api/events/404404"))

                .andExpect(status().isNotFound())
        ;
    }

    @Test
    @TestDescription("update event to correct")
    public void updateEvent() throws Exception {

        // Given
        Event event = this.generateEvent(200);
        EventDto eventDto = this.modelMapper.map(event, EventDto.class);
        String eventName = "update Event";
        eventDto.setName(eventName);

        // When

        this.mockMvc.perform(put("/api/events/{id}", event.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(eventDto))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(eventName))
                .andExpect(jsonPath("_links.self").exists());

    }

    @Test
    @TestDescription("update event when event is empty")
    public void updateEvent400Empty() throws Exception {

        // Given
        Event event = this.generateEvent(200);
        EventDto eventDto = new EventDto();
        // When

        this.mockMvc.perform(put("/api/events/{id}", event.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(eventDto))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    @TestDescription("update event when event is wrong")
    public void updateEvent400Wrong() throws Exception {

        // Given
        Event event = this.generateEvent(200);

        EventDto eventDto = this.modelMapper.map(event, EventDto.class);
        eventDto.setBasePrice(20000);
        eventDto.setMaxPrice(1000);


        // When)

        this.mockMvc.perform(put("/api/events/{id}", event.getId())
                .contentType((MediaType.APPLICATION_JSON_VALUE))
                .content(this.objectMapper.writeValueAsString(eventDto))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @TestDescription("update event when event is not exist")
    public void updateEvent404() throws Exception {

        // Given
        Event event = this.generateEvent(200);

        EventDto eventDto = this.modelMapper.map(event, EventDto.class);


        // When

        this.mockMvc.perform(put("/api/events/987654321")
                .contentType((MediaType.APPLICATION_JSON_VALUE))
                .content(this.objectMapper.writeValueAsString(eventDto))
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
