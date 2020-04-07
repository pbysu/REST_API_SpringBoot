package me.bysu.restAPI.configs;

import me.bysu.restAPI.accounts.Account;
import me.bysu.restAPI.accounts.AccountRole;
import me.bysu.restAPI.accounts.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

        // password 앞에 여러 프리픽스를 추가해서 어떠한 인코딩을 사용 되었는지 알려줌.
    }

    @Bean
    public ApplicationRunner applicationRunner(){
        return new ApplicationRunner() {

            @Autowired
            AccountService accountService;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                Account bysu = Account.builder()
                        .email("bysu@email.com")
                        .password("bysu")
                        .roles(Set.of(AccountRole.ADMIN, AccountRole.USERS))
                        .build();
                accountService.saveAccount(bysu);
            }
        };
    }

}
