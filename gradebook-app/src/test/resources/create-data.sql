INSERT INTO T_TEACHERS(TEACHER_ID, TEACHER_NAME, TEACHER_LASTNAME, TEACHER_SUBJECTNAME)
        VALUES ('1', 'Alex', 'Petrof', 'English');
INSERT INTO T_TEACHERS(TEACHER_ID, TEACHER_NAME, TEACHER_LASTNAME, TEACHER_SUBJECTNAME)
        VALUES ('2', 'Taras', 'Ivanoy', 'Italian');

INSERT INTO T_GROUPS(G_ID, G_NAME, TEACHER_ID)
        VALUES ('group1', 'group A', '1');
INSERT INTO T_GROUPS(G_ID, G_NAME, TEACHER_ID)
        VALUES ('group2', 'group B', '1');

INSERT INTO T_STUDENTS(S_ID, S_NAME, S_LASTNAME, G_ID)
        VALUES ('student1', 'Igor', 'Timofeev', 'group1');
INSERT INTO T_STUDENTS(S_ID, S_NAME, S_LASTNAME, G_ID)
        VALUES ('student2', 'Petr', 'Voinich', 'group1');
INSERT INTO T_STUDENTS(S_ID, S_NAME, S_LASTNAME, G_ID)
        VALUES ('student3', 'Alex', 'Prohorov', 'group2');
INSERT INTO T_STUDENTS(S_ID, S_NAME, S_LASTNAME, G_ID)
        VALUES ('student4', 'Dmitry', 'Lobovich', 'group2');

INSERT INTO T_EXAMS(E_ID, E_DATE, E_GRADE, S_ID)
        VALUES ('exam1', null, '7', 'student1');
INSERT INTO T_EXAMS(E_ID, E_DATE, E_GRADE, S_ID)
        VALUES ('exam2', null, '10', 'student2');
INSERT INTO T_EXAMS(E_ID, E_DATE, E_GRADE, S_ID)
        VALUES ('exam3', null, '8', 'student3');
INSERT INTO T_EXAMS(E_ID, E_DATE, E_GRADE, S_ID)
        VALUES ('exam4', null, '5', 'student4');
