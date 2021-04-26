package org.hillel.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@EqualsAndHashCode(exclude = {"groups", "posts", "topics", "votes"})
@ToString(exclude = { "groups", "posts", "topics", "votes"})
public class User
{
  @Id
  @GeneratedValue(generator="increment")
  @GenericGenerator(name="increment", strategy = "increment")
  private long id;

  @Column(name = "user_login")
  private String login;

  @Column
  private String password;

  @Column(name = "registration_date")
  private LocalDateTime registrationDate;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<Post> posts;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<Topic> topics;


  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
  @JoinTable (name="users_group",
          joinColumns=@JoinColumn (name="user_id"),
          inverseJoinColumns=@JoinColumn(name="group_id"))
  private Set<Group> groups;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<Votes> votes;

  public long getId()
  {
    return id;
  }

  public void setId(long id)
  {
    this.id = id;
  }

  public String getLogin()
  {
    return login;
  }

  public void setLogin(String login)
  {
    this.login = login;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public Set<Post> getPosts()
  {
    return posts;
  }

  public void setPosts(Set<Post> posts)
  {
    this.posts = posts;
  }

  public Set<Topic> getTopics()
  {
    return topics;
  }

  public void setTopics(Set<Topic> topics)
  {
    this.topics = topics;
  }

  public LocalDateTime getRegistrationDate()
  {
    return registrationDate;
  }

  public void setRegistrationDate(LocalDateTime registrationDate)
  {
    this.registrationDate = registrationDate;
  }

  public Set<Group> getGroups()
  {
    return groups;
  }

  public void setGroups(Set<Group> groups)
  {
    this.groups = groups;
  }

  public Set<Votes> getVotes()
  {
    return votes;
  }

  public void setVotes(Set<Votes> votes)
  {
    this.votes = votes;
  }
}