public class SchoolStudent {
    // Поля класса
    private String district;
    private String school;
    private String county;
    private String grades;
    private int students;
    private double teachers;
    private double calworks;
    private double lunch;
    private int computer;
    private double expenditure;
    private double income;
    private double english;
    private double read;
    private double math;

    // Конструктор
    public SchoolStudent(String district, String school, String county, String grades, int students,
                         double teachers, double calworks, double lunch, int computer, double expenditure,
                         double income, double english, double read, double math) {
        this.district = district;
        this.school = school;
        this.county = county;
        this.grades = grades;
        this.students = students;
        this.teachers = teachers;
        this.calworks = calworks;
        this.lunch = lunch;
        this.computer = computer;
        this.expenditure = expenditure;
        this.income = income;
        this.english = english;
        this.read = read;
        this.math = math;
    }

    // Геттеры для всех полей
    public String getDistrict() { return district; }
    public String getSchool() { return school; }
    public String getCounty() { return county; }
    public String getGrades() { return grades; }
    public int getStudents() { return students; }
    public double getTeachers() { return teachers; }
    public double getCalworks() { return calworks; }
    public double getLunch() { return lunch; }
    public int getComputer() { return computer; }
    public double getExpenditure() { return expenditure; }
    public double getIncome() { return income; }
    public double getEnglish() { return english; }
    public double getRead() { return read; }
    public double getMath() { return math; }
}