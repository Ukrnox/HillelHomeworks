package forum.controllers;

import forum.entities.Group;
import forum.entities.Topic;
import forum.entities.User;
import forum.services.GroupService;
import forum.services.TopicService;
import forum.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(path = "/group")
public class GroupController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    GroupService groupService;

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;

    @GetMapping("/{groupId}")
    private ModelAndView showGroupTopics(@PathVariable("groupId") Long groupId,
                                         @CookieValue(value = "login", defaultValue = "1") String login,
                                         @CookieValue(value = "password", defaultValue = "1") String password,
                                         HttpServletResponse response)
    {
        User user = userService.checkPerson(login, password);
        ModelAndView modelAndView = new ModelAndView();
        Group group = groupService.findById(groupId);
        if (user != null && group != null)
        {
            Cookie activeGroupCookie = new Cookie("activeGroup", String.valueOf(group.getId()));
            activeGroupCookie.setPath("/");
            activeGroupCookie.setMaxAge(24 * 60 * 60);
            response.addCookie(activeGroupCookie);

            modelAndView = createForumActiveGroupModelAndView(user,
                    group.getId(),
                    groupService.findAll(),
                    topicService.findTopicByGroupId(groupId));
        }
        else
        {
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }

    public static ModelAndView createForumActiveGroupModelAndView(User user, Long activeGroupId,
                                                                  List<Group> allGroups, List<Topic> topicByGroupId)
    {
        ModelAndView modelAndView = UserController.createForumModelAndView(user, allGroups);
        modelAndView.addObject("activeGroupId", activeGroupId);
        modelAndView.addObject("topics", topicByGroupId);
        return modelAndView;
    }
}