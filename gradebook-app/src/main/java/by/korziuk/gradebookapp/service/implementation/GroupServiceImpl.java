package by.korziuk.gradebookapp.service.implementation;

import by.korziuk.gradebookapp.data.GroupRepository;
import by.korziuk.gradebookapp.model.Group;
import by.korziuk.gradebookapp.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Override
    public List<Group> list() {
        log.info("Fetching all groups");
        return groupRepository.findAll();
    }

    @Override
    public Group create(Group group) {
        log.info("Saving new group: {}", group.getName());
        return groupRepository.save(group);
    }

    @Override
    public Group get(String id) {
        log.info("Fetching group by ID: {}", id);
        return groupRepository.findById(id).orElse(null);
    }

    @Override
    public Group update(Group group) {
        log.info("Updating group: {}", group.getName());
        return groupRepository.save(group);
    }

    @Override
    public Boolean delete(String id) {
        log.info("Deleting group by ID: {}", id);
        groupRepository.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public Boolean existsGroupByName(String name) {
        log.info("Does group exist by Name: {}", name);
        if (groupRepository.existsGroupByName(name)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
