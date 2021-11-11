package task.repositories;

import task.models.BookLending;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookLendingRepository {
    private Connection connection;

    public BookLendingRepository(Connection connection) {
        this.connection = connection;
    }

    private RowMapper<BookLending> bookLendingRowMapper = row -> {

        Long id = row.getLong("id");
        Long studentID = row.getLong("student_id");
        Long bookID = row.getLong("book_id");
        String lendingDate = String.valueOf(row.getDate("lending_date"));
        String returningDate = String.valueOf(row.getDate("returning_date"));
        return new BookLending(id, studentID, bookID, lendingDate, returningDate);
    };

    public List<BookLending> findAll() {
        List<BookLending> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from book_lending");

            while (resultSet.next()) {
                BookLending bookLending = bookLendingRowMapper.mapRow(resultSet);
                result.add(bookLending);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }
}
