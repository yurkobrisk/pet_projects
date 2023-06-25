package by.korziuk.gradebookapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupExamsDTO {
    private String id;
    private String name;
    private String teacherId;
    private String teacherName;
    private String teacherLastName;
    private String date;
    private Map<String, String[]> exams; // studentId, [studentName, studentLastName, studentGrade]
}
