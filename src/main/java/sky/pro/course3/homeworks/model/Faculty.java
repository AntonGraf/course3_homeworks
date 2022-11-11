package sky.pro.course3.homeworks.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String color;

    @OneToMany(mappedBy = "faculty")
    @JsonBackReference
    private Collection<Student> students;

    public Faculty(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Faculty() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Collection<Student> getStudents() {
        return students;
    }

    public void setStudents(Collection<Student> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Faculty faculty = (Faculty) o;

        if (id != faculty.id) return false;
        if (name != null ? !name.equals(faculty.name) : faculty.name != null) return false;
        return color != null ? color.equals(faculty.color) : faculty.color == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", students=" + students.stream().map(Student::getId).collect(Collectors.toList()) +
                '}';
    }
}
