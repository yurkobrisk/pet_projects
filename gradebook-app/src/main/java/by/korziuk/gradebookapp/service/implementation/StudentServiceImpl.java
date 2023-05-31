package by.korziuk.gradebookapp.service.implementation;

import by.korziuk.gradebookapp.data.StudentRepository;
import by.korziuk.gradebookapp.model.Student;
import by.korziuk.gradebookapp.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public Student create(Student student) {
        log.info("Saving new student: {} {}", student.getName(), student.getLastName());
        return studentRepository.save(student);
    }

    @Override
    public Collection<Student> list(int limit) {
        log.info("Fetching {} students", limit);
        return studentRepository.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public List<Student> list() {
        log.info("Fetching all students");
        return studentRepository.findAll();
    }

    @Override
    public Student get(String id) {
        log.info("Fetching student by ID: {}", id);
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student update(Student student) {
        log.info("Updating student: {} {}", student.getName(), student.getLastName());
        return studentRepository.save(student);
    }

    @Override
    public Boolean delete(String id) {
        log.info("Deleting student by ID: {}", id);
        studentRepository.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public List<Student> findStudentsByGroupId(String id) {
        return studentRepository.findStudentsByGroupId(id);
    }
}
