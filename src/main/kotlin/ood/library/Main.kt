package ood.library
fun main(args: Array<String>) {
    print("Hello World")
}

enum class BookFormat {
    HARDCOVER, PAPERBACK, AUDIO_BOOK, EBOOK, NEWSPAPER, MAGAZINE, JOURNAL
}

enum class BookStatus {
    AVAILABLE, RESERVED, LOANED, LOST
}

enum class ReservationStatus {
    COMPLETED, WAITING, PENDING, CANCELED, NONE
}

enum class AccountStatus {
    ACTIVE, CLOSED, CANCELED, BLACKLISTED, NONE
}


class Address {
    private val streetAddress: String? = null
    private val city: String? = null
    private val state: String? = null
    private val zipCode: String? = null
    private val country: String? = null
}

class Person {
    private val name: String? = null
    private val address: Address? = null
    private val email: String? = null
    private val phone: String? = null
}

object Constants {
    const val MAX_BOOKS_ISSUED_TO_A_USER = 5
    const val MAX_LENDING_DAYS = 10
    const val MAX_RETURNING_DAYS = 14
}