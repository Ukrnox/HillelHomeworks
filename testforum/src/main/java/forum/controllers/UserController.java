package forum.controllers;


import forum.entities.Group;
import forum.entities.User;
import forum.services.GroupService;
import forum.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @GetMapping(path = "/regPage")
    public ModelAndView regPage()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @GetMapping(path = "/login")
    public ModelAndView login(@RequestParam String login,
                              @RequestParam String password,
                              HttpServletResponse response)
    {
        User user = userService.checkPerson(login, password);

        ModelAndView modelAndView = new ModelAndView();
        if (user == null)
        {
            modelAndView.addObject("wrongInformation", "Wrong Information!");
            modelAndView.setViewName("index");
        }
        else
        {
            Cookie[] loginAndPasswordCookie = createLoginAndPasswordCookie(login, password);
            response.addCookie(loginAndPasswordCookie[0]);
            response.addCookie(loginAndPasswordCookie[1]);
            modelAndView = createForumModelAndView(user, groupService.findAll());
        }
        return modelAndView;
    }

    @GetMapping(path = "/reg")
    public ModelAndView registration(@RequestParam String login,
                                     @RequestParam String password,
                                     @RequestParam String checkPassword,
                                     HttpServletResponse response)
    {
        User newUser = new User();
        ModelAndView modelAndView = new ModelAndView();
        if (login.equals("") || password.equals(""))
        {
            modelAndView.addObject("noInfo", "You need to fill in all the fields!");
            modelAndView.setViewName("registration");
        }
        else if (userService.checkLogin(login))
        {
            modelAndView.addObject("loginExists", "Login Exists!");
            modelAndView.setViewName("registration");
        }
        else if (!password.equals(checkPassword))
        {
            modelAndView.addObject("passwordMismatch", "Password Mismatch!");
            modelAndView.setViewName("registration");
        }
        else
        {
            newUser.setLogin(login);
            newUser.setPassword(password);
            newUser.setRegistrationDate(LocalDateTime.now());
            userService.save(newUser);
            Cookie[] loginAndPasswordCookie = createLoginAndPasswordCookie(login, password);
            response.addCookie(loginAndPasswordCookie[0]);
            response.addCookie(loginAndPasswordCookie[1]);
            modelAndView = createForumModelAndView(newUser, groupService.findAll());
        }
        return modelAndView;
    }

    public static Cookie[] createLoginAndPasswordCookie(String login, String password)
    {
        Cookie cookieLogin = new Cookie("login", login);
        Cookie cookiePassword = new Cookie("password", password);
        cookieLogin.setPath("/");
        cookiePassword.setPath("/");
        cookieLogin.setMaxAge(24 * 60 * 60);
        cookiePassword.setMaxAge(24 * 60 * 60);
        return new Cookie[]{cookieLogin, cookiePassword};
    }

    public static ModelAndView createForumModelAndView(User user, List<Group> allGroups)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("groups", allGroups);
        modelAndView.addObject("userName", user.getLogin());
        modelAndView.setViewName("forum");
        return modelAndView;
    }
}