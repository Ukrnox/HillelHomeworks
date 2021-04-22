package org.hillel.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;


@EqualsAndHashCode(exclude = {"author", "topic", "votes"})
@Entity
@ToString(exclude = {"author", "topic", "votes"})
@Table(name = "posts")
public class Post
{
  @Id
  @GeneratedValue(generator="increment")
  @GenericGenerator(name="increment", strategy = "increment")
  private long id;

  @Column
  private String text;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User author;

  @ManyToOne
  @JoinColumn(name = "topic_id")
  private Topic topic;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<Votes> votes;

  @Column(name = "created_date")
  private LocalDateTime dateCreated;

  public Set<Votes> getVotes()
  {
    return votes;
  }

  public void setVotes(Set<Votes> votes)
  {
    this.votes = votes;
  }

  public long getId()
  {
    return id;
  }

  public void setId(long id)
  {
    this.id = id;
  }

  public User getAuthor()
  {
    return author;
  }

  public void setAuthor(User author)
  {
    this.author = author;
  }

  public Topic getTopic()
  {
    return topic;
  }

  public void setTopic(Topic topic)
  {
    this.topic = topic;
  }

  public String getText()
  {
    return text;
  }

  public void setText(String text)
  {
    this.text = text;
  }

  public LocalDateTime getDateCreated()
  {
    return dateCreated;
  }

  public void setDateCreated(LocalDateTime dateCreated)
  {
    this.dateCreated = dateCreated;
  }

}