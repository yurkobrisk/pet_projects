package by.korziuk.gradebookapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupTeacherDTO {
    private String id;
    private String name;
    private String teacherId;
}
