package forum.services;

import forum.entities.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:springContext.xml"})
public class UserServiceTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceTest.class);

    @Autowired
    private UserService userService;


    @Test
    public void simpleTest()
    {
//        List<User> users = userService.findUsers();
//        users.forEach(c-> System.out.println(c.getId()+" : "+c.getPassword()));
//        LOGGER.info("DONE!");
        System.out.println("Done!");
    }
}