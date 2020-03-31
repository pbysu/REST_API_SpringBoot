#7d

## What is REST DOCS

Spring REST Docs helps you to document RESTful services.


We can set about RestDocs
below setting is prettyPrint about json.


```java
@TestConfiguration
public class RestDocsConfiguration {

    @Bean
    public RestDocsMockMvcConfigurationCustomizer restDocsMockMvcConfigurationCustomizer() {

        return configurer -> configurer.operationPreprocessors()
                .withRequestDefaults(prettyPrint())
                .withResponseDefaults(prettyPrint());
    }

/*     return new RestDocsMockMvcConfigurationCustomizer() {
        @Override
        public void customize(MockMvcRestDocumentationConfigurer configurer) {
            configurer.operationPreprocessors()
                    .withRequestDefaults(prettyPrint())
                    .withResponseDefaults(prettyPrint());
        }
    };*/
```

in EventControllerTest

```java

.andDo(document("create-event",
                links(
                        linkWithRel("self").description("link to self"),
                        linkWithRel("query-events").description("link to query"),
                        linkWithRel("update-events").description("link to update existing event")

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
                        fieldWithPath("_links.self.href").description("link to self event")
                       // fieldWithPath("_links.profile.href").description("link to profile")
                )

        ))

```
