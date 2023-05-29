package by.korziuk.gradebookapp.dto;

import by.korziuk.gradebookapp.model.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupExamsDTO {
    private String id;
    private String name;
    private String teacherId;
    private String teacherName;
    private List<Student> students;

}
