 
# This project from inflrean

This lecture title is "REST API using SpringBoot"

lecture made by keesun
# 6d branch

## HATEOAS

Hypermedia As The Engine Of Application State

Not Use HATEOAS

```json
{
  "name": "jun"
}
```

Use HATEOAS

```json
{
  "name": "ABCD",
  "links": [
    {
      "rel": "self",
      "href": "http://localhost:8080/user"
    },
    {
      "rel": "delete",
      "href": "http://localhost:8080/user/delete"
    },
    {
      "rel": "update",
      "href": "http://localhost:8080/user/update"
    }
  ]
}

```

## EventResource class

This class inherit EntityModel<Event> apply HATEOAS and has JsonUnwrapped.

JsonUnwrapped make your json do not in capsule lik {}.

using add method make events have self link

## Controller

```java


        // HATEOAS ~~
        WebMvcLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(newEvent.getId());
        URI createdUri = selfLinkBuilder.toUri();
        EventResource eventResource = new EventResource(event);
        eventResource.add(linkTo(EventController.class).withRel("query-events"));
        eventResource.add(selfLinkBuilder.withRel("update-events"));
        return ResponseEntity.created(createdUri).body(eventResource);

```
 first of all we make selfLinkBuilder use linkTo, getId
 
 URI is value like http://localhost:portNumber/(RequestMapping value)/(getId)
 
```java
return ResponseEntity.created(createdUri).body(eventResource);
```

finally return value is

```json
{
  "id":1,
  "name":"Spring",
  "description":"REST API Development",
  "beginEnrollmentDateTime":"2010-11-23T14:23:00",
  "closeEnrollmentDateTime":"2018-11-30T14:23:00",
  "beginEventDateTime":"2018-12-05T14:30:00",
  "endEventDateTime":"2018-12-06T14:30:00",
  "location":"D Start up Factory",
  "basePrice":100,
  "maxPrice":200,
  "limitOfEnrollment":100,
  "offline":true,
  "free":false,
  "eventStatus":"DRAFT",
  "_links":{
    "self":{
      "href":"http://localhost/api/events/1"
    },
    "query-events":{
      "href":"http://localhost/api/events"
    },
    "update-events":{
      "href":"http://localhost/api/events"
    }
  }
}


// 출처: https://engkimbs.tistory.com/866 [새로비]

```
 
 
