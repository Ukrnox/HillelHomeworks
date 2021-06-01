package forum.controllers;

import forum.entities.Group;
import forum.entities.User;
import forum.services.GroupService;
import forum.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping(path = "/")
public class StartAndExitController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(StartAndExitController.class);

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ModelAndView start(@CookieValue(value = "login", defaultValue = "1") String login,
                              @CookieValue(value = "password", defaultValue = "1") String password)
    {
        User user = userService.checkPerson(login, password);
        ModelAndView modelAndView = new ModelAndView();
        if (user != null)
        {
            List<Group> allGroups = groupService.findAll();
            modelAndView.addObject("userName", user.getLogin());
            modelAndView.addObject("groups", allGroups);
            modelAndView.setViewName("forum");
        }
        else
        {
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }

    @GetMapping(path = "/exit")
    public ModelAndView exit(HttpServletResponse response)
    {
        Cookie exitCookieLogin = new Cookie("login","0");
        Cookie exitCookiePassword = new Cookie("password","0");
        Cookie exitCookieActiveGroup = new Cookie("activeGroup","0");
        exitCookieLogin.setMaxAge(1);
        exitCookiePassword.setMaxAge(1);
        exitCookieActiveGroup.setMaxAge(1);
        response.addCookie(exitCookieLogin);
        response.addCookie(exitCookiePassword);
        response.addCookie(exitCookieActiveGroup);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
