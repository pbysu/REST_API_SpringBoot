# This project from inflrean

This lecture title is "REST API using SpringBoot"

lecture made by keesun


## ResponseEntity

https://a1010100z.tistory.com/106


ResponseEntity inherits HttpEntity, so It has 'header' and 'body'

conTentType, accept are located in header

https://dololak.tistory.com/630

Accept : It is what is request type

Content-Type : It is what is http response type


in my code

```spring
        mockMvc.perform(post("/api/events/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(objectMapper.writeValueAsString(event))
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists());
    }
```


## REST and URI

https://developer.yahoo.com/social/rest_api_guide/uri-general.html

Each resource is identified by one or more Uniform Resource Identifiers (URIs).

https://spoqa.github.io/2013/06/11/more-restful-interface.html

```spring
    public ResponseEntity createEvent(@RequestBody Event event){
        URI createdUri = linkTo(EventController.class).slash("{id}").toUri();
        event.setId(10);
        return ResponseEntity.created(createdUri).body(event);
    }
```


## MockMvc

https://itmore.tistory.com/entry/MockMvc-%EC%83%81%EC%84%B8%EC%84%A4%EB%AA%85

MockMvc that can work the behavior without deploying in to the application server



## jackson

need to set dependency for using jackson
```maven
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
			<exclusions>
				<exclusion>
					<groupId>org.junit.vintage</groupId>
					<artifactId>junit-vintage-engine</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
```

starter include jackson 

if you want to another jackson version you need to add dependency like

```maven
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
    <version>2.10.3</version>
</dependency>

```

after add dependency, you can use ObjectMapper

```springboot
    @Autowired // objectMapper auto set bean
    ObjectMapper objectMapper;

    objectMapper.writeValueAsString(event)

```

