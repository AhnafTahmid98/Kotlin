// Assignment 2: filter, map, reduce

// Returns only the even numbers from the list
fun getEvenNumbers(numbers: List<Int>): List<Int> =
    numbers.filter { it % 2 == 0 }

// Returns a list where each number is squared
fun getSquares(numbers: List<Int>): List<Int> =
    numbers.map { it * it }

// Returns the sum of all numbers in the list
fun sumNumbers(numbers: List<Int>): Int =
    numbers.reduce { acc, n -> acc + n }

fun main() {
    val numbers = (1..100).toList()

    val evens = getEvenNumbers(numbers)
    val squares = getSquares(numbers)
    val sum = sumNumbers(numbers)

    println("Original numbers:")
    println(numbers)

    println("\nEvens:")
    println(evens)

    println("\nSquares:")
    println(squares)

    println("\nSum:")
    println(sum)
}
