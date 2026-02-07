package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.repository.UserRepositry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class userSeviceTest {

    @Autowired
    private UserRepositry userRepositry;
    @Disabled
    @Test
    public void testFindByUserName(){
        Assertions.assertNotNull(userRepositry.findByUsername("yash"));
    }

    @ParameterizedTest
    @CsvSource({
            "1,2,3",
            "2,3,5"
    })
    public void test(int a,int b,int expected){
        Assertions.assertEquals(expected,a+b);
    }
}
