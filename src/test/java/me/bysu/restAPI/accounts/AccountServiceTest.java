package me.bysu.restAPI.accounts;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class AccountServiceTest {

    @Rule // 3 using junit
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;



    @Test
    public void findByUsername() {

        // Given
        String password = "bysu";
        String username = "bysu@email.com";
        Account account = Account.builder()
                .email(username)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USERS))
                .build();
        // save
        this.accountRepository.save(account);

        // When
        UserDetailsService userDetailsService = accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        //Then

        assertThat(userDetails.getPassword()).isEqualTo(password);
    }


    // 1 : @Test(expected = UsernameNotFoundException.class)
    @Test
    public void findByuserNameFail() {
        String username = "random@email.com";

        // 2
/*요
        try {
            accountService.loadUserByUsername(username);

            fail("supposed to be failed");
        } catch (UsernameNotFoundException e) {
            assertThat(e.getMessage()).containsSequence(username);
        }
*/


        // 선 예측 필
        expectedException.expect(UsernameNotFoundException.class);

        expectedException.expectMessage(Matchers.containsString(username));

        accountService.loadUserByUsername(username);
    }

}