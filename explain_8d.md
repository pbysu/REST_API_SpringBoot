 
 
# 8d branch


## param - ControllerTest

```java

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

get("/api/events")
                .param("page", "1") // 0,1  so 1
                .param("size", "10")
                .param("sort", "name,DESC")

```

MockHttpServletRequestBuilder Obj {

URI : /api/events

parameters : page, size, sort

}

MockHttpServletRequestBuilder 객체는 parameter 에 key, value 값으로 저장을 한다.

## pageAble, PagedResourceAssembler -  EventController 

```java
this.eventRepository.findAll(pageable);

```

eventRepository : JpaRepository 를 상속 받고 있음

JPA paging 정렬 기준으로 정렬해서 page 크기를 정해서 구별하고 몇 번째 페이지를 넘겨줘라

> page : sort, page, size

JpaRepository findAll(T) : return Page<Class> List

> page 기준에 맞는 Entity들 을 넘겨 준다

```java
var pagedResources = assembler.toModel(page, e -> new EventResource(e));
```

PagedResourcesAssembler

PagedResources 는 pageAble 의 집합

PagedResource는 pageAble을 전달하는 DTO

PagedResourcesAssembler는 이런 변환 과정을 가능하게 하는 작업.

Page<Event> page : total, content (각각의 Enity 정보), pageAble (size, snumber, sort)
 

pagedResources.content : metadata(size, totalElements, totalPages, number), content, links(first, prev, self, next, last)


```java 
    ResponseEntity.ok(something)
```

builder 형식 : something 을 저장했을 때 build 가 되는지 확인


@Ignore 함수 단위 등 테스트 동작을 하지 않고 넘어간다.

