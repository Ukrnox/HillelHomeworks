package forum.controllers;

import forum.entities.Post;
import forum.entities.Topic;
import forum.entities.User;
import forum.entities.Vote;
import forum.services.PostService;
import forum.services.TopicService;
import forum.services.UserService;
import forum.services.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/topic/post")
public class PostController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private PostService postsService;

    @Autowired
    private VoteService voteService;

    @GetMapping(path = "/addPost")
    public ModelAndView addNewTopic(@CookieValue(value = "login", defaultValue = "1") String login,
                                    @CookieValue(value = "password", defaultValue = "1") String password,
                                    @CookieValue(value = "activeTopicId", defaultValue = "0") Long activeTopicId,
                                    @CookieValue(value = "activeGroup", defaultValue = "0") Long activeGroup,
                                    @RequestParam String newPostText,
                                    HttpServletRequest request)
    {
        User user = userService.checkPerson(login, password);
        Topic activeTopic = topicService.findById(activeTopicId);
        List<Topic> topicByGroupId = topicService.findTopicByGroupId(activeGroup);
        ModelAndView modelAndView = new ModelAndView();
        if (user != null && activeTopic != null)
        {
            if (!newPostText.equals(""))
            {
                Post newPost = new Post();
                newPost.setAuthor(user);
                newPost.setTopic(activeTopic);
                newPost.setText(newPostText);
                newPost.setDateCreated(LocalDateTime.now());
                postsService.save(newPost);
            }
            List<Post> postsByTopicId = postsService.findPostsByTopicId(activeTopicId);
            List<Vote> votesByTopicId = voteService.findVoteByTopicId(activeTopicId);
            modelAndView = TopicController.createForumActiveTopicModelAndView(user, activeTopicId, postsByTopicId, topicByGroupId,
                    votesByTopicId);
        }
        else
        {
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }

    @GetMapping(path = "/remove/{postId}")
    public ModelAndView addNewTopic(@PathVariable("postId") Long postId,
                                    @CookieValue(value = "login", defaultValue = "1") String login,
                                    @CookieValue(value = "password", defaultValue = "1") String password,
                                    @CookieValue(value = "activeTopicId", defaultValue = "0") Long activeTopicId,
                                    @CookieValue(value = "activeGroup", defaultValue = "0") Long activeGroup,
                                    HttpServletRequest request)
    {
        User user = userService.checkPerson(login, password);
        List<Topic> topicByGroupId = topicService.findTopicByGroupId(activeGroup);
        ModelAndView modelAndView = new ModelAndView();
        Post postById = postsService.findPostById(postId);
        if (user != null)
        {
            if (postById != null && postById.getAuthor().getLogin().equals(login))
            {
                postsService.removePostById(postId);
            }
            List<Post> postsByTopicId = postsService.findPostsByTopicId(activeTopicId);
            List<Vote> votesByTopicId = voteService.findVoteByTopicId(activeTopicId);
            modelAndView = TopicController.createForumActiveTopicModelAndView(user, activeTopicId, postsByTopicId, topicByGroupId,
                    votesByTopicId);
        }
        else
        {
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }
}
