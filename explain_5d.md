# 5d

##  Apply business logic to tests 

### you can see it like service

How to assign values in entity to use method

in my case it is in Event's update method. 

## Testing with parameters

### you have to watch out version of junit

junit 5 is different version 4 how to set parameters .

```java

@RunWith(JUnitParamsRunner.class)


    private Object[] paramsForTestFree(){
        return new Object[]{
                new Object[] {0,0,true},
                new Object[] {100,0,false},
                new Object[] {0,100,false}
        };
    }

    @Test
    @@Parameters(method = "paramsForTestFree")

```

## I need to again re-view createEvent in EventController

```java
@RequestMapping(value ="/api/events/", produces = MediaTypes.HAL_JSON_VALUE)

class ...

 // PostMapping
     @PostMapping
 
     // @RequestBody : Want to get value from json
     // @Valid : Want to verify input value using @NotEmpty @NotNull in EventDto
     // ResponseEntity : Have HttpHeader and body by inheriting HttpEntity
     public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, Errors errors){
         if(errors.hasErrors()){
             return ResponseEntity.badRequest().body(errors);
         }
 
         // This error case is made by me
         eventValidator.validate(eventDto, errors);
         if(errors.hasErrors()){
             return ResponseEntity.badRequest().body(errors);
         }
 
         // modelMapper : library to help me for minute labor
         Event event = modelMapper.map(eventDto, Event.class);
 
         // values update by method.
         event.update();
         Event newEvent = this.eventRepository.save(event);
 
         // HATEOAS ~~
         WebMvcLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(newEvent.getId());
         URI createdUri = selfLinkBuilder.toUri();
 
         EventResource eventResource = new EventResource(event);
         eventResource.add(linkTo(EventController.class).withRel("query-events"));
 
         eventResource.add(selfLinkBuilder.withRel("update-events"));
         return ResponseEntity.created(createdUri).body(eventResource);
     }
```
