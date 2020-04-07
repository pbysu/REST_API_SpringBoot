
# 9d branch

## JPA Annotation

### ManyToOne

 단반향 사용 @ManyToone??????????????????????

## Security

# web, method security

web : Filter 기반

method : proxy 기반

인증을 통해 권환을 확인해주는 시점에 대한 차이.

servlet 기반의 웹에 대한 보안

## Security Context Holder

Thread local 구현체

데이터를 공유하는 단위

한 쓰레드 위라면 매개변수로 넘겨주지 않아도 사용 가능

Spring Security 내용 더 찾아보기!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

## Security interceptor (Method, Filter)

### Method Security interceptor

### Filter Security Interceptor

## User Details Service

AccountService implements UserDetailsService

 @EqualsAndHashCode(of ="id")??
 
 
  @ElementCollection(fetch = FetchType.EAGER)
  
 여러개 역할 가능 , EAGER? 자주 불러올꺼라서??
 
     @ManyToOne
     private Account manager;
     
org.springframework.security.core.userdetails.User

의존성을 추가하면 스프링 부트 자동 시큐리티 설정, 모든 요청에서 인증이 필요하게 만

인증 되지 않는 모든 테스트는 오류가 나게 됨.

public class SecurityConfig extends WebSecurityConfigurerAdapter 

을 추가 하면 스프링 부트 자동 시큐리티 설정은 사라지게 됨

package me.bysu.restAPI.configs;

import me.bysu.restAPI.accounts.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public TokenStore tokenStore(){
        return new InMemoryTokenStore();
    }



      @Override
      public void configure(WebSecurity web) throws Exception {
          web.ignoring().mvcMatchers("/docs/index.html");
          web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  
      }
구조 밑 으어.. 함수들 파악..하기


org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: Invocation of init method failed; nested exception is org.hibernate.service.spi.ServiceException: Unable to create requested service [org.hibernate.engine.jdbc.env.spi.JdbcEnvironment]

docker가 작동 안하고 있어서 에러 뜬거... 

error  해결법 ..

docker restart rest 