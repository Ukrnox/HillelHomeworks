package forum.services;

import forum.controllers.VoteController;
import forum.entities.Vote;
import forum.repositories.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VoteService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(VoteService.class);

    @Autowired
    private VoteRepository voteRepository;

    @Transactional
    public List<Vote> findVoteByUserID(Long userId)
    {
        return voteRepository.findVotesByUserId(userId);
    }

    public Vote findVotesByUserAndPostId(Long userId, Long postId)
    {
        return voteRepository.findVotesByUserAndPostId(userId, postId);
    }

    @Transactional
    public void changeVoteById(Long downvotes, Long upvotes, Long voteId)
    {
        voteRepository.changeVoteById(downvotes, upvotes, voteId);
    }

    @Transactional
    public Vote save(Vote vote)
    {
        return voteRepository.save(vote);
    }

    public List<Vote> findVoteByTopicId(Long topicID)
    {
        return voteRepository.findVoteByTopicId(topicID);
    }

    @Transactional
    public void removeVoteById(Long voteId)
    {
        voteRepository.removeVoteById(voteId);
    }
}
