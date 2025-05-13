import java.sql.*;
import java.util.List;

public class SQL {
    private Connection connection;  // Приватное поле для соединения с БД

    public SQL(String dbName) throws SQLException {
        // Установка соединения с SQLite базой данных
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
    }

    // Геттер для соединения
    public Connection getConnection() {
        return connection;
    }

    // Создание таблицы школ
    public void createTable() throws SQLException {
        // Явно удаляем старую таблицу, если она существует
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS schools");
        }

        // Создаем новую таблицу с правильной структурой
        String sql = "CREATE TABLE schools (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "district TEXT NOT NULL, " +
                "school TEXT NOT NULL, " +
                "county TEXT NOT NULL, " +
                "grades TEXT, " +
                "students INTEGER, " +
                "teachers REAL, " +
                "calworks REAL, " +
                "lunch REAL, " +
                "computer INTEGER, " +
                "expenditure REAL, " +
                "income REAL, " +
                "english REAL, " +
                "read REAL, " +
                "math REAL)";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    // Вставка данных в таблицу
    public void insertData(List<SchoolStudent> schools) throws SQLException {
        String sql = "INSERT INTO schools (district, school, county, grades, students, " +
                "teachers, calworks, lunch, computer, expenditure, income, english, read, math) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (SchoolStudent school : schools) {
                pstmt.setString(1, school.getDistrict());
                pstmt.setString(2, school.getSchool());
                pstmt.setString(3, school.getCounty());
                pstmt.setString(4, school.getGrades());
                pstmt.setInt(5, school.getStudents());
                pstmt.setDouble(6, school.getTeachers());
                pstmt.setDouble(7, school.getCalworks());
                pstmt.setDouble(8, school.getLunch());
                pstmt.setInt(9, school.getComputer());
                pstmt.setDouble(10, school.getExpenditure());
                pstmt.setDouble(11, school.getIncome());
                pstmt.setDouble(12, school.getEnglish());
                pstmt.setDouble(13, school.getRead());
                pstmt.setDouble(14, school.getMath());
                pstmt.executeUpdate();
            }
        }
    }

    // Задание 1: Средние расходы в указанных округах
    public void averageExpenditureInCounties() throws SQLException {
        String sql = "SELECT county, AVG(expenditure) as avg_expenditure " +
                "FROM schools " +
                "WHERE county IN ('Fresno', 'Contra Costa', 'El Dorado', 'Glenn') " +
                "AND expenditure > 10 " +
                "GROUP BY county";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Средние расходы в округах (где расходы > 10):");
            while (rs.next()) {
                System.out.printf("%-15s %.2f%n",
                        rs.getString("county"),
                        rs.getDouble("avg_expenditure"));
            }
        }
    }

    // Задание 2: Школы с лучшими показателями по математике в диапазонах
    public void topMathSchoolsInRanges() throws SQLException {
        String sql1 = "SELECT school, math FROM schools " +
                "WHERE students BETWEEN 5000 AND 7500 " +
                "ORDER BY math DESC LIMIT 1";

        String sql2 = "SELECT school, math FROM schools " +
                "WHERE students BETWEEN 10000 AND 11000 " +
                "ORDER BY math DESC LIMIT 1";

        System.out.println("\nШколы с наивысшими баллами по математике в диапазонах:");

        // Запрос для диапазона 5000-7500 студентов
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql1)) {
            if (rs.next()) {
                System.out.printf("5000-7500 студентов: %-40s Математика: %.1f%n",
                        rs.getString("school"),
                        rs.getDouble("math"));
            }
        }

        // Запрос для диапазона 10000-11000 студентов
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql2)) {
            if (rs.next()) {
                System.out.printf("10000-11000 студентов: %-35s Математика: %.1f%n",
                        rs.getString("school"),
                        rs.getDouble("math"));
            }
        }
    }

    // Закрытие соединения с БД
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}