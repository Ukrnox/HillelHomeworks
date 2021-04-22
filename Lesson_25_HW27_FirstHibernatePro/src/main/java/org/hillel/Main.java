package org.hillel;

import org.hillel.dao.*;
import org.hillel.dao.GroupDao;
import org.hillel.entity.Group;
import org.hillel.entity.Post;
import org.hillel.entity.Topic;
import org.hillel.entity.User;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("********************** createUsers ****************************"+"\n");

        User user = new User();
        user.setLogin("FirstUser");
        user.setPassword("1111111");
        user.setRegistrationDate(LocalDateTime.now());

        User user2 = new User();
        user2.setLogin("SecondUser");
        user2.setPassword("2222222222");
        user2.setRegistrationDate(LocalDateTime.now());

        UserDao userDao = new UserDao();
        userDao.create(user);
        userDao.create(user2);

        System.out.println(userDao.read(1));
        System.out.println(userDao.read(2));
        System.out.println();
        System.out.println("******** createGroupsAndAddUser#1 ****************************"+"\n");

        Group group = new Group();
        group.setName("FirstGroup");
        GroupDao groupDao = new GroupDao();
        groupDao.create(group);
        System.out.println(groupDao.read(1));
        Group group2 = new Group();
        group2.setName("SecondGroup");
        groupDao.create(group2);
        System.out.println(groupDao.read(2));

        User readUser1ForAddGroup = userDao.read(1);
        readUser1ForAddGroup.getGroups().add(groupDao.read(1));
        readUser1ForAddGroup.getGroups().add(groupDao.read(2));
        userDao.update(readUser1ForAddGroup);

        User readUser2ForAddGroup = userDao.read(2);
        readUser2ForAddGroup.getGroups().add(groupDao.read(1));
        readUser2ForAddGroup.getGroups().add(groupDao.read(2));
        userDao.update(readUser2ForAddGroup);
        System.out.println();
        System.out.println("*************** createTopic ****************************"+"\n");

        Topic topic = new Topic();
        topic.setTitle("FirstTopic");
        topic.setDateCreated(LocalDateTime.now());
        TopicDao td = new TopicDao();
        topic.setGroup(groupDao.read(1));

        User readUser = userDao.read(1);
        topic.setAuthor(readUser);
        readUser.getTopics().add(topic);
        userDao.update(readUser);

        System.out.println(td.read(1));
        System.out.println();
        System.out.println("*************** createPost ******************************"+"\n");

        PostDao postDao = new PostDao();

        Post post = new Post();
        post.setText("FirstPost");
        post.setDateCreated(LocalDateTime.now());
        post.setAuthor(userDao.read(1));
        post.setTopic(td.read(1));

        Post post2 = new Post();
        post2.setText("SecondPost");
        post2.setDateCreated(LocalDateTime.now());
        post2.setAuthor(userDao.read(1));
        post2.setTopic(td.read(1));

        postDao.create(post);
        postDao.create(post2);

        System.out.println(postDao.read(1));
        System.out.println(postDao.read(2));
        System.out.println();
        System.out.println("******************** createAndUpdateVotes ******************" + "\n");

        userDao.createOrUpdateVote(1, 1, false);
        userDao.createOrUpdateVote(2, 1, false);
        userDao.createOrUpdateVote(1, 1, true);

        VotesDao votesDao = new VotesDao();
        HashMap<String, Long> votes = votesDao.calculateVotes(1);
        System.out.println(votes);
        System.out.println();
        System.out.println("****************** checkDb *******************************" + "\n");
        System.out.println("User#1 groups : " + userDao.read(1).getGroups() + "\n");
        System.out.println("Group#1 users : " + groupDao.read(1).getUsers() + "\n");
        System.out.println("Vote#1 : " + votesDao.read(1) + "\n");
        System.out.println("Vote#2 : " + votesDao.read(2) + "\n");
        System.out.println("User#1 topics : " + userDao.read(1).getTopics() + "\n");
        System.out.println("User#1 posts : " + userDao.read(1).getPosts() + "\n");
        System.out.println("User#1 votes : " + userDao.read(1).getVotes() + "\n");
        System.out.println("Post#1 votes : " + postDao.read(1).getVotes() + "\n");
        System.out.println("Topic#1 posts : " + td.read(1).getPosts() + "\n");
        System.out.println("Topic#1 author : " + td.read(1).getAuthor());
    }
}
