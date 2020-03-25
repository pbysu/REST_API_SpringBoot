# 3d branch

## DTO DAO Entity

https://gmlwjd9405.github.io/2018/12/25/difference-dao-dto-entity.html

DTO : Data Access Object

DTA : Data Transfer Object
      It use when DB data move to SERVICE, CONTROLLER etc..
        have getter, setter
      in my case EventDto make setter/getter use @Data
      
Entity : It is linked by DB's table

https://velog.io/@jayjay28/%EC%97%94%ED%8B%B0%ED%8B%B0Entity

@Id : primary key as pk

@GeneratedValue : auto generate by DB

## JpaRepository

If inherited , you do not need to set @Repository

It have functions like save, findOne, findAll, count,delete

JpaRepository<Object, Id-pk>

## @Mock @MockBean @SpringBootTest

https://jojoldu.tistory.com/226
https://meetup.toast.com/posts/124

@mockBean is fakeBean

... I haven't fully understood it yet. 