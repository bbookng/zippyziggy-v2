package com.zippyziggy.monolithic;


import com.zippyziggy.monolithic.common.util.SecurityUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SecurityUtilTest {

    @Autowired
    SecurityUtil securityUtil;

    @Test
    void getUUIDTest() {
        securityUtil.getCurrentMember();
    }
}
