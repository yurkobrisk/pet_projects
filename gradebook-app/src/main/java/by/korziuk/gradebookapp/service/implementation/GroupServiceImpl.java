package by.korziuk.gradebookapp.service.implementation;

import by.korziuk.gradebookapp.data.GroupRepository;
import by.korziuk.gradebookapp.model.Group;
import by.korziuk.gradebookapp.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    @Override
    public Collection<Group> list() {
        log.info("Fetching all groups");
        return groupRepository.findAll();
    }
}
