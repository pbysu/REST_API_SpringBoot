 
#7d branch

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
                       ...
                ),
                requestHeaders(
                  ..
                ),
                requestFields(
                  ...
                ),
                responseHeaders(
                ...=
```


## Using AsciiDocs

AsciiDocs can be replaced by MarkDown

AsciiDocs find the link of adoc by {snippets}

I modified mvn package to use it more efficiently

check my pom.xml's plugins and

link : https://docs.spring.io/spring-restdocs/docs/2.0.3.RELEASE/reference/html5/#getting-started-build-configuration

+ setting-plugin-AsciiDoc : I apply like MarkDown preview. 

## Docker & postgresql

need to docker so install Docker follow link

https://docs.docker.com/install/


docker run --name rest -p 5432:5432 -e POSTSGRES_PASSWORD=pass -d postgres

docker exec -i -t rest bash
> p : port number

> i : interactive mode, use I/O standard like keyboard and monitor

> t : Teletypewriter(TTY) easy to think, It is terminal

> e : set any environment variable in the container by using one or more -e flags

> d : containers started in detached mode exit when the root process used to run the container exits, unless you also specify the --rm option.
 
> exec : some cmd execution
>

 ```
 su - postgres
 psql -d postgres -U postgres
 \l
 \dt
 
 port 5432 -d demon mode 
 

```


# Set application

we build using progresql but we need setting to use H2 when test.
 
```json
         <groupId>com.h2database</groupId>
         <artifactId>h2</artifactId>
         <scope>test</scope>

            <groupId>org.postgresql</groupId>
         <artifactId>postgresql</artifactId>
         <scope>runtime</scope>
```

and I make two properties set to build each case.

#### A file with a different name is needed to override rather than overwrite

compare below tow files

> main.resources.application.properties

> test.resources.application-test.properties
 

when you want to use test properties, you just write @ActiveProfiles("test") on the class.

## Make Error have link of index

To do it, I make ErrosResource.class

in there, I define 

```java
  add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
```

in EventController, Errors is covered by ErrorsResource
```java
    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }
```
