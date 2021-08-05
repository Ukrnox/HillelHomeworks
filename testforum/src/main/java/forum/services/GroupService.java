package forum.services;

import forum.entities.Group;
import forum.repositories.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    private GroupRepository groupRepository;

    public List<Group> findAll()
    {
        return groupRepository.findAll();
    }

    public Group findById(Long groupId)
    {
        Group group = null;
        Optional<Group> byId = groupRepository.findById(groupId);
        if (byId.isPresent())
        {
            group = byId.get();
        }
        return group;
    }
}