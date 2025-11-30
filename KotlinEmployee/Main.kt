// Simple Employee example with inheritance and mapOf

// Base class: Employee
open class Employee(
    protected val name: String,       // employee's name
    protected val baseSalary: Double  // fixed base salary
) {
    // By default, salary is just the base salary
    open fun calculateSalary(): Double = baseSalary

    // Public helper so we can use the name outside
    fun getEmployeeName(): String = name
}

// Full-time employee: base salary + bonus
class FullTimeEmployee(
    name: String,
    baseSalary: Double,
    private val bonus: Double
) : Employee(name, baseSalary) {

    // Salary = base salary + bonus
    override fun calculateSalary(): Double = baseSalary + bonus
}

// Part-time employee: hourly rate * hours worked
class PartTimeEmployee(
    name: String,
    private val hourlyRate: Double,
    private val hoursWorked: Int
) : Employee(name, baseSalary = 0.0) {

    // Salary = hourlyRate * hoursWorked
    override fun calculateSalary(): Double = hourlyRate * hoursWorked
}

fun main() {
    // Create some employees
    val emp1 = FullTimeEmployee("Alice", baseSalary = 3000.0, bonus = 500.0)
    val emp2 = FullTimeEmployee("Bob", baseSalary = 2800.0, bonus = 300.0)
    val emp3 = PartTimeEmployee("Charlie", hourlyRate = 20.0, hoursWorked = 80)
    val emp4 = PartTimeEmployee("Diana", hourlyRate = 18.5, hoursWorked = 60)

    // mapOf: key = employee name, value = salary
    val salaries = mapOf(
        emp1.getEmployeeName() to emp1.calculateSalary(),
        emp2.getEmployeeName() to emp2.calculateSalary(),
        emp3.getEmployeeName() to emp3.calculateSalary(),
        emp4.getEmployeeName() to emp4.calculateSalary()
    )

    println("Employee salaries:")
    for ((name, salary) in salaries) {
        println("$name: ${"%.2f".format(salary)} €")
    }
}
