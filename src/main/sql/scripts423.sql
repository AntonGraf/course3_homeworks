SELECT student.name, student.age, faculty.name FROM student
    INNER JOIN faculty on faculty.id = student.faculty_id;

SELECT student.name, student.age, avatar.file_path FROM avatar
    INNER JOIN student on avatar.student_id = student.id;