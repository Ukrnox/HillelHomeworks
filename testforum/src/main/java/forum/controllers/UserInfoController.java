package forum.controllers;

import forum.entities.Post;
import forum.entities.Topic;
import forum.entities.User;
import forum.services.PostService;
import forum.services.TopicService;
import forum.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/userinfo")
public class UserInfoController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @RequestMapping("/")
    public ModelAndView userInfoPage(@CookieValue(value = "login", defaultValue = "1") String login,
                                     @CookieValue(value = "password", defaultValue = "1") String password)
    {
        User user = userService.checkPerson(login, password);
        ModelAndView modelAndView = new ModelAndView();
        if (user != null)
        {
            modelAndView.addObject("userName", login);
            modelAndView.addObject("editeduserinfo", "hidden=\"true\"");
            modelAndView.setViewName("userInfo");
        }
        else
        {
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }

    @RequestMapping("/topics")
    public ModelAndView userShowTopics(@CookieValue(value = "login", defaultValue = "1") String login,
                                       @CookieValue(value = "password", defaultValue = "1") String password)
    {
        User user = userService.checkPerson(login, password);
        ModelAndView modelAndView = new ModelAndView();
        if (user != null)
        {
            List<Topic> topicByUserId = topicService.findTopicByUserId(user.getId());
            modelAndView.addObject("editeduserinfo", "hidden=\"true\"");
            modelAndView.addObject("userName", login);
            modelAndView.addObject("topics", topicByUserId);
            modelAndView.setViewName("userInfo");
        }
        else
        {
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }


    @RequestMapping("/posts")
    public ModelAndView userShowPosts(@CookieValue(value = "login", defaultValue = "1") String login,
                                      @CookieValue(value = "password", defaultValue = "1") String password)
    {
        User user = userService.checkPerson(login, password);
        ModelAndView modelAndView = new ModelAndView();
        if (user != null)
        {
            List<Post> postsByUserId = postService.findPostsByUserId(user.getId());
            modelAndView.addObject("userName", login);
            modelAndView.addObject("posts", postsByUserId);
            modelAndView.addObject("editeduserinfo", "hidden=\"true\"");
            modelAndView.setViewName("userInfo");
        }
        else
        {
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }

    @RequestMapping("/edituserconfig")
    public ModelAndView userShowUserConfig(@CookieValue(value = "login", defaultValue = "1") String login,
                                           @CookieValue(value = "password", defaultValue = "1") String password)
    {
        User user = userService.checkPerson(login, password);
        ModelAndView modelAndView = new ModelAndView();
        if (user != null)
        {
            modelAndView.addObject("userName", login);
            modelAndView.addObject("editeduserinfo", "");
            modelAndView.setViewName("userInfo");
        }
        else
        {
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }

    @RequestMapping("/changeuserlogin")
    public ModelAndView changeUserLogin(@CookieValue(value = "login", defaultValue = "1") String login,
                                        @CookieValue(value = "password", defaultValue = "1") String password,
                                        @RequestParam String newLogin,
                                        @RequestParam String userPassword,
                                        HttpServletResponse response)
    {
        User user = userService.checkPerson(login, password);
        ModelAndView modelAndView = new ModelAndView();
        if (user != null)
        {
            modelAndView.addObject("userName", user.getLogin());
            modelAndView.addObject("editeduserinfo", "");
            if (!userPassword.equals(password))
            {
                String wrongInformation = "Wrong password!";
                modelAndView.addObject("wrongInformation", wrongInformation);
            }
            else if (userService.checkLogin(newLogin))
            {
                String wrongInformation = "Login exist!";
                modelAndView.addObject("wrongInformation", wrongInformation);
            }
            else
            {
//                String editedNewLogin = "'" + newLogin + "'";
                userService.changeUserLogin(newLogin, user.getId());
                User updateUser = userService.checkPerson(newLogin, password);
                if (updateUser != null)
                {
                    modelAndView.addObject("userName", updateUser.getLogin());
                    Cookie[] loginAndPasswordCookie = UserController.createLoginAndPasswordCookie(newLogin, password);
                    response.addCookie(loginAndPasswordCookie[0]);
                    response.addCookie(loginAndPasswordCookie[1]);
                }
            }
            modelAndView.setViewName("userInfo");
        }
        else
        {
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }

    @RequestMapping("/changeuserpassword")
    public ModelAndView changeUserPassword(@CookieValue(value = "login", defaultValue = "1") String login,
                                           @CookieValue(value = "password", defaultValue = "1") String password,
                                           @RequestParam String oldPassword,
                                           @RequestParam String newPassword,
                                           @RequestParam String confirmPassword,
                                           HttpServletResponse response)
    {
        User user = userService.checkPerson(login, password);
        ModelAndView modelAndView = new ModelAndView();
        if (user != null)
        {
            modelAndView.addObject("userName", user.getLogin());
            modelAndView.addObject("editeduserinfo", "");
            if (!oldPassword.equals(password))
            {
                String wrongChangePasswordInformation = "Wrong old password!";
                modelAndView.addObject("wrongChangePasswordInformation", wrongChangePasswordInformation);
            }
            else if (!newPassword.equals(confirmPassword) || newPassword.equals(""))
            {
                String wrongChangePasswordInformation = "Wrong information!";
                modelAndView.addObject("wrongChangePasswordInformation", wrongChangePasswordInformation);
            }
            else
            {
                userService.changeUserPassword(newPassword, user.getId());
                User updateUser = userService.checkPerson(login, newPassword);
                if (updateUser != null)
                {
                    modelAndView.addObject("userName", updateUser.getLogin());
                    Cookie[] loginAndPasswordCookie = UserController.createLoginAndPasswordCookie(updateUser.getLogin(),
                            updateUser.getPassword());
                    response.addCookie(loginAndPasswordCookie[0]);
                    response.addCookie(loginAndPasswordCookie[1]);
                }
            }
            modelAndView.setViewName("userInfo");
        }
        else
        {
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }
}
