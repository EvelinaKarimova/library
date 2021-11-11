package task.repositories;

import task.models.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentsRepository {
    private final Connection connection;

    public StudentsRepository(Connection connection) {
        this.connection = connection;
    }

    private final RowMapper<Student> studentRowMapper = row -> {
        Long id = row.getLong("id");
        String lastName = row.getString("last_name");
        String firstName = row.getString("first_name");
        String patronymic = row.getString("patronymic");
        return new Student(id, lastName, firstName, patronymic);
    };

    public List<Student> findAll() {
        List<Student> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from student");

            while (resultSet.next()) {
                Student student = studentRowMapper.mapRow(resultSet);
                result.add(student);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

}

