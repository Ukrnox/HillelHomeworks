package forum.repositories;

import forum.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long>
{
    @Query(value = "Select * from votes where post_id In (SELECT post_id FROM topics WHERE id = :topicId);",
            nativeQuery = true)
    List<Vote> findVoteByTopicId(@Param("topicId") Long topicId);

    @Query(value = "SELECT * FROM votes WHERE user_id = :user_id", nativeQuery = true)
    List<Vote> findVotesByUserId(@Param("user_id") Long userId);

    @Query(value = "SELECT * FROM votes WHERE user_id = :user_id AND post_id = :post_Id", nativeQuery = true)
    Vote findVotesByUserAndPostId(@Param("user_id") Long userId, @Param("post_Id") Long postId);

    @Modifying
    @Query(value = "UPDATE votes SET  downvotes = :downvotes, upvotes = :upvotes WHERE id = :voteId",
            nativeQuery = true)
    void changeVoteById(@Param("downvotes") Long downvotes,
                        @Param("upvotes") Long upvotes,
                        @Param("voteId") Long voteId);

    @Modifying
    @Query(value = "DELETE FROM votes WHERE id = :voteId", nativeQuery = true)
    void removeVoteById(@Param("voteId") Long voteId);
}
