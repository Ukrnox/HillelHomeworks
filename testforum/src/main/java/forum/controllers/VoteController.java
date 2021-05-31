package forum.controllers;

import forum.entities.Post;
import forum.entities.Topic;
import forum.entities.User;
import forum.entities.Vote;
import forum.repositories.PostRepository;
import forum.repositories.VoteRepository;
import forum.services.PostService;
import forum.services.TopicService;
import forum.services.UserService;
import forum.services.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("topic/post/vote")
public class VoteController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(VoteController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private PostService postsService;

    @Autowired
    private VoteService voteService;

    @Autowired
    private PostService postService;

    @GetMapping(path = "/{postId}")
    public ModelAndView addVote(@PathVariable("postId") Long postId,
                                @CookieValue(value = "login", defaultValue = "1") String login,
                                @CookieValue(value = "password", defaultValue = "1") String password,
                                @RequestParam("like") Long like,
                                HttpServletResponse response)
    {
        User user = userService.checkPerson(login, password);
        Post post = postsService.findPostById(postId);
        ModelAndView modelAndView = new ModelAndView();
        if (user != null && post != null)
        {
            Vote vote = voteService.findVotesByUserAndPostId(user.getId(), postId);
            if (vote != null)
            {
                if ((like == 1 && vote.getUpVotes() == 1) || (like == 0 && vote.getDownVotes() == 1))
                {
                    voteService.removeVoteById(vote.getId());
                }
                else
                {
                    voteService.changeVoteById(like == 1L ? 0L : 1L, like == 1L ? 1L : 0L, vote.getId());
                }
            }
            else
            {
                Vote newVote = new Vote();
                newVote.setAuthor(user);
                newVote.setPost(post);
                newVote.setDownVotes(like == 1L ? 0L : 1L);
                newVote.setUpVotes(like == 1L ? 1L : 0L);
                voteService.save(newVote);
            }
            long activeTopicId = post.getTopic().getId();
            Topic activeTopic = topicService.findById(activeTopicId);
            List<Topic> topics = topicService.findTopicByGroupId(activeTopic.getGroup().getId());
            List<Post> postsByTopicId = postsService.findPostsByTopicId(activeTopicId);
            List<Vote> votesByTopicId = voteService.findVoteByTopicId(activeTopicId);
            modelAndView = TopicController.createForumActiveTopicModelAndView(user, activeTopicId, postsByTopicId, topics, votesByTopicId);
        }
        else
        {
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }
}