package by.korziuk.gradebookapp.service.implementation;

import by.korziuk.gradebookapp.data.TeacherRepository;
import by.korziuk.gradebookapp.model.Teacher;
import by.korziuk.gradebookapp.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

import static java.lang.Boolean.*;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Override
    public Teacher create(Teacher teacher) {
        log.info("Saving new teacher: {} {}", teacher.getName(), teacher.getLastName());
        return teacherRepository.save(teacher);
    }

    @Override
    public Collection<Teacher> list(int limit) {
        log.info("Fetching all teachers");
        return teacherRepository.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Teacher get(String id) {
        log.info("Fetching teacher by ID: {}", id);
        return teacherRepository.findById(id).orElse(null);
    }

    @Override
    public Teacher update(Teacher teacher) {
        log.info("Updating teacher: {} {}", teacher.getName(), teacher.getLastName());
        return teacherRepository.save(teacher);
    }

    @Override
    public Boolean delete(String id) {
        log.info("Deleting teacher by ID: {}", id);
        teacherRepository.deleteById(id);
        return TRUE;
    }
}
