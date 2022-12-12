package by.korziuk.gradebookapp.data;

import by.korziuk.gradebookapp.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, String> {
}
