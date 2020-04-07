package me.bysu.restAPI.configs;

import me.bysu.restAPI.accounts.AccountService;
import me.bysu.restAPI.common.BaseControolerTest;
import me.bysu.restAPI.common.TestDescription;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AuthServerConfigTest extends BaseControolerTest {

    @Autowired
    AccountService accountService;


    /// 2way of 6 way be used

    @Test
    @TestDescription("Test to recieve authentication token")
    public void getAuthToken(){
        //... to be continue
    }
}