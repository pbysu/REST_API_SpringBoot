 
 
# 4d

## done testing for sublist

@TestDescription("normal create event test")

@TestDescription("Test to catch unwanted value input errors")

@TestDescription("Test to catch that the required value do not include input")


## Validation annotation

write @valid in controller 

```java
  @PostMapping
    public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, Errors errors)
```

in Entity Class

```java
 @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotNull
    private LocalDateTime beginEnrollmentDateTime;
    @NotNull
    private LocalDateTime closeEnrollmentDateTime;
    ...

```

more information https://appsnuri.tistory.com/115

## Serialization and Deserialization

Serialization :  mechanism of converting the state of an object into a byte stream

Deserialization : machanism of converting the state of byte stream into a state object

how to know what is serialization class : 
    inherited Serializable, "implements serializable"
    inherited class of implements serializable

```java
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors);
        }
```
Errors do not have serializer, so It occurred runtime error

so We need method to make Errors be serializer.

###  JsonSerializer<Errors>  

https://docs.oracle.com/javaee/7/api/javax/json/stream/JsonGenerator.html

JSON arrays can be created using JsonGenerator by calling the writeStartArray() method and then adding values with the write method.

jsonGenerator

```jsava

.writeStartObject("address")
             .write("streetAddress", "21 2nd Street")
             .write("city", "New York")
             .write("state", "NY")
             .write("postalCode", "10021")
         .writeEnd()
```

```json
"address" : {
       "streetAddress": "21 2nd Street",
       "city": "New York",
       "state": "NY",
       "postalCode": "10021"
   }

```


> I do not know yet what is diff global error and local error

# Custom Annotation

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface TestDescription {

    String value();
}
```
https://jdm.kr/blog/216

@Target : 어노테이션이 적용할 위치를 결정합니다.

@Retention : 어떤 시점까지 어노테이션이 영향을 미치는지 결정합니다.
