import java.sql.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class Chart {
    private Connection connection;

    public Chart(Connection connection) {
        this.connection = connection;
    }

    // Метод для отображения среднего количества студентов по округам
    public void showAverageStudentsByCounty() throws SQLException {
        // SQL-запрос для получения данных
        String sql = "SELECT county, AVG(students) as avg_students " +
                "FROM schools " +
                "GROUP BY county " +
                "LIMIT 10";  // Берем первые 10 округов

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // Заполнение данных для графика
            while (rs.next()) {
                dataset.addValue(rs.getDouble("avg_students"),
                        "Среднее количество",
                        rs.getString("county"));
            }
        }

        // Создание столбчатой диаграммы
        JFreeChart chart = ChartFactory.createBarChart(
                "Среднее количество студентов по округам",  // Заголовок
                "Округ",                                   // Ось X
                "Среднее количество студентов",            // Ось Y
                dataset                                   // Данные
        );

        // Создание и отображение окна с графиком
        ChartFrame frame = new ChartFrame("Статистика школ", chart);
        frame.pack();
        frame.setVisible(true);
    }
}