package forum.controllers;


import forum.entities.*;
import forum.repositories.PostRepository;
import forum.repositories.VoteRepository;
import forum.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/topic")
public class TopicController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(TopicController.class);

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private PostService postService;

    @Autowired
    private VoteService voteService;

    @GetMapping(path = "/{topicId}")
    public ModelAndView showPosts(@PathVariable("topicId") Long topicId,
                                  @CookieValue(value = "login", defaultValue = "1") String login,
                                  @CookieValue(value = "password", defaultValue = "1") String password,
                                  HttpServletResponse response)
    {
        User user = userService.checkPerson(login, password);
        Topic activeTopic = topicService.findById(topicId);
        ModelAndView modelAndView = new ModelAndView();
        if (user != null && activeTopic != null)
        {
            List<Topic> topics = topicService.findTopicByGroupId(activeTopic.getGroup().getId());
            long activeTopicId = activeTopic.getId();
            List<Post> postsByTopicId = postService.findPostsByTopicId(activeTopicId);
            List<Vote> votesByTopicId = voteService.findVoteByTopicId(activeTopicId);
            Cookie activeTopicIdCookie = new Cookie("activeTopicId", String.valueOf(activeTopicId));
            response.addCookie(activeTopicIdCookie);
            modelAndView = createForumActiveTopicModelAndView(user, activeTopicId, postsByTopicId, topics, votesByTopicId);
        }
        else
        {
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }

    @GetMapping(path = "/addTopic")
    public ModelAndView addNewTopic(@CookieValue(value = "login", defaultValue = "1") String login,
                                    @CookieValue(value = "password", defaultValue = "1") String password,
                                    @CookieValue(value = "activeGroup", defaultValue = "0") String activeGroupId,
                                    @RequestParam String newTopicTitle,
                                    HttpServletRequest request)
    {
        User user = userService.checkPerson(login, password);
        List<Group> allGroups = groupService.findAll();
        Group activeGroup = null;
        ModelAndView modelAndView = new ModelAndView();
        if (!activeGroupId.equals("0"))
        {
            activeGroup = groupService.findById(Long.parseLong(activeGroupId));
        }

        if (user != null && activeGroup != null && !newTopicTitle.equals(""))
        {
            Topic newTopic = new Topic();
            newTopic.setTitle(newTopicTitle);
            newTopic.setDateCreated(LocalDateTime.now());
            newTopic.setGroup(activeGroup);
            newTopic.setAuthor(user);
            topicService.save(newTopic);
            modelAndView = GroupController.createForumActiveGroupModelAndView(user,
                    activeGroup.getId(),
                    allGroups,
                    topicService.findTopicByGroupId(activeGroup.getId()));
        }
        else if (user != null && activeGroup != null)
        {
            modelAndView = GroupController.createForumActiveGroupModelAndView(user,
                    activeGroup.getId(),
                    allGroups,
                    topicService.findTopicByGroupId(activeGroup.getId()));
        }
        else
        {
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }

    public static ModelAndView createForumActiveTopicModelAndView(User user,
                                                                  Long activeTopicId,
                                                                  List<Post> postsByTopicId,
                                                                  List<Topic> topics,
                                                                  List<Vote> votes)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userName", user.getLogin());
        modelAndView.addObject("activeTopicId", activeTopicId);
        modelAndView.addObject("topics", topics);
        modelAndView.addObject("votes", votes);
        modelAndView.addObject("posts", postsByTopicId);
        modelAndView.setViewName("activeTopic");
        return modelAndView;
    }
}
