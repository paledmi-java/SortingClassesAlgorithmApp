package classesToSort;

public final class Employee {
    private final String name;
    private final String position;
    private final int salary;

    public Employee(EmployeeBuilder employeeBuilder) {
        this.name = employeeBuilder.name;
        this.position = employeeBuilder.position;
        this.salary = employeeBuilder.salary;
    }

    public static class EmployeeBuilder{
        private String name;
        private String position;
        private int salary;

        public EmployeeBuilder name(String name){
            this.name = name;
            return this;
        }

        public EmployeeBuilder position(String position){
            this.position = position;
            return this;
        }

        public EmployeeBuilder salary(int salary){
            this.salary = salary;
            return this;
        }
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public int getSalary() {
        return salary;
    }
}
