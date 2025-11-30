fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false

    var i = 3
    while (i * i <= n) {
        if (n % i == 0) return false
        i += 2
    }
    return true
}

fun main() {
    // 1) Find first 50 primes
    val primes = mutableListOf<Int>()
    var number = 2
    while (primes.size < 50) {
        if (isPrime(number)) {
            primes.add(number)
        }
        number++
    }

    println("First 50 prime numbers:")
    println(primes)

    // 2) First 50 even numbers: 2, 4, 6, ..., 100
    val evens = (1..50).map { it * 2 }

    println("\nFirst 50 even numbers:")
    println(evens)

    // 3) Combine primes + evens
    val combined = primes.toMutableList()
    combined.addAll(evens)

    println("\nCombined list of primes and evens:")
    println(combined)
}
