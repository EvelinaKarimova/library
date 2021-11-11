package task;

import task.models.BookLending;
import task.models.EvilStudent;
import task.models.Student;
import task.repositories.BookLendingRepository;
import task.repositories.StudentsRepository;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Connection connection = getConnection();

        StudentsRepository studentsRepository = new StudentsRepository(connection);
        List<Student> students = studentsRepository.findAll();
        BookLendingRepository bookLendingRepository = new BookLendingRepository(connection);
        List<BookLending> bookLending = bookLendingRepository.findAll();
        System.out.println("Злостный читатель - " + findEvilStudent(students, bookLending));
    }

    public static String findEvilStudent(List<Student> students, List<BookLending> bookLending) {
        ArrayList<EvilStudent> evilStudents = new ArrayList<>();
        for (Student s : students) {
            evilStudents.add(new EvilStudent(s.getId(), s.getLastName(), s.getFirstName(), s.getPatronymic(), 0, 0));
        }
        for (BookLending bl : bookLending) {
            String lending = bl.getLendingDate();
            String returning = bl.getReturningDate();
            int days;
            if (returning.equals("null")) {
                days = days(lending, String.valueOf(LocalDate.now()));
            } else days = days(lending, returning);
            if (days > 14) {
                EvilStudent s = evilStudents.get((int) (bl.getStudentID() - 1));
                s.setTimes(s.getTimes() + 1);
                s.setNumberOfDays(s.getNumberOfDays() + days);
            }
        }
        int max = 0;
        int d;
        Long id = null;
        for (EvilStudent es : evilStudents) {
            d = es.getNumberOfDays();
            if (d > max) {
                max = d;
                id = es.getId();
            }
        }
        if (id != null) return evilStudents.get((int) (id - 1)).getLastName();
        else return "Не найден!";
    }

    public static int days(String lending, String returning) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE;
        LocalDate d1 = LocalDate.parse(lending, dateTimeFormatter);
        LocalDate d2 = LocalDate.parse(returning, dateTimeFormatter);
        return (int) ChronoUnit.DAYS.between(d1, d2);
    }

    public static Connection getConnection() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("C:\\Users\\PC\\Desktop\\library_task\\src\\main\\java\\db.properties"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");
        String url = properties.getProperty("db.url");
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}

