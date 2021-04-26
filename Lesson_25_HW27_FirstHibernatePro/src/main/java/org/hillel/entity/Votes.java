package org.hillel.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(
        name = "votes",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"post_id", "user_id"})}
)
@EqualsAndHashCode
@ToString
public class Votes
{
  @Id
  @GeneratedValue(generator="increment")
  @GenericGenerator(name="increment", strategy = "increment")
  private long id;

  @Column
  private long upVotes;

  @Column
  private long downVotes;

  @ManyToOne
  @JoinColumn(name = "post_id")
  private Post post;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User author;

  public long getId()
  {
    return id;
  }

  public void setId(long id)
  {
    this.id = id;
  }

  public void setUpVotes(long upVotes)
  {
    this.upVotes = upVotes;
  }

  public void setDownVotes(long downVotes)
  {
    this.downVotes = downVotes;
  }

  public long getUpVotes()
  {
    return upVotes;
  }

  public void setUpVotes(int upVotes)
  {
    this.upVotes = upVotes;
  }

  public long getDownVotes()
  {
    return downVotes;
  }

  public void setDownVotes(int downVotes)
  {
    this.downVotes = downVotes;
  }

  public Post getPost()
  {
    return post;
  }

  public void setPost(Post post)
  {
    this.post = post;
  }

  public User getAuthor()
  {
    return author;
  }

  public void setAuthor(User author)
  {
    this.author = author;
  }
}
