package by.korziuk.gradebookapp.service.implementation;

import by.korziuk.gradebookapp.data.ExamRepository;
import by.korziuk.gradebookapp.model.Exam;
import by.korziuk.gradebookapp.service.ExamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;

    @Override
    public List<Exam> list() {
        log.info("Fetching all exams");
        return examRepository.findAll();
    }

    @Override
    public Exam create(Exam exam) {
        log.info("Saving new exams: {}", exam.getId());
        return examRepository.save(exam);
    }

    @Override
    public Exam get(String id) {
        log.info("Fetching exam by ID: {}", id);
        return examRepository.findById(id).orElse(null);
    }

    @Override
    public Exam update(Exam exam) {
        log.info("Updating exam by ID: {}", exam.getId());
        return examRepository.save(exam);
    }

    @Override
    public Boolean delete(String id) {
        log.info("Deleting exam by ID: {}", id);
        examRepository.deleteById(id);
        return Boolean.TRUE;
    }
}
