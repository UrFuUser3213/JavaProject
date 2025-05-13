import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<SchoolStudent> schools = new ArrayList<>();

        // Чтение данных из CSV файла
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\deads\\OneDrive\\Рабочий стол\\library\\школы.csv"))) {
            // Пропускаем заголовок
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                // Разделяем строку на значения
                String[] values = line.split(",");

                // Парсим данные (удаляем кавычки)
                String district = values[1].replace("\"", "");
                String school = values[2].replace("\"", "");
                String county = values[3].replace("\"", "");
                String grades = values[4].replace("\"", "");
                int students = Integer.parseInt(values[5].replace("\"", ""));
                double teachers = Double.parseDouble(values[6].replace("\"", ""));
                double calworks = Double.parseDouble(values[7].replace("\"", ""));
                double lunch = Double.parseDouble(values[8].replace("\"", ""));
                int computer = Integer.parseInt(values[9].replace("\"", ""));
                double expenditure = Double.parseDouble(values[10].replace("\"", ""));
                double income = Double.parseDouble(values[11].replace("\"", ""));
                double english = Double.parseDouble(values[12].replace("\"", ""));
                double read = Double.parseDouble(values[13].replace("\"", ""));
                double math = Double.parseDouble(values[14].replace("\"", ""));

                // Создаем объект SchoolStudent и добавляем в список
                schools.add(new SchoolStudent(district, school, county, grades, students,
                        teachers, calworks, lunch, computer, expenditure, income, english, read, math));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Работа с базой данных
        try {
            // Создаем объект для работы с БД
            SQL sql = new SQL("school.db");

            // Создаем таблицу и заполняем данными
            sql.createTable();
            sql.insertData(schools);

            // Создаем график
            Chart chart = new Chart(sql.getConnection());
            chart.showAverageStudentsByCounty();

            // Выполняем задания
            sql.averageExpenditureInCounties();
            sql.topMathSchoolsInRanges();

            // Закрываем соединение с БД
            sql.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}