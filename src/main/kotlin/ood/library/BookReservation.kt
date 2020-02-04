package ood.library

import java.time.LocalDateTime

class BookReservation {
    var createdAt = LocalDateTime.now()
    var status = ReservationStatus.NONE
    var barcode : String? = null
    var memberid: String? = null
    constructor(b:String, m:String) {
        barcode = b
        memberid = m
    }
    constructor()
    companion object {
        private var bookReservations = mutableMapOf<String, BookReservation>()
        fun fetchReservationDetails(barcode : String) : BookReservation? {
            if(!bookReservations.containsKey(barcode)) return null
            return bookReservations[barcode]
        }
        fun makeReservation(barcode: String, memberID:String) : Boolean {
            if(bookReservations.containsKey(barcode)) {
                if(bookReservations[barcode]?.memberid == memberID) {
                    println("You've reserved ${barcode}")
                } else {
                    println("You cannot reserve ${barcode}")
                }
                return false
            }
            bookReservations[barcode] = BookReservation(barcode, memberID)
            return true
        }
        fun sendBookAvailabileNotificaton() : Unit {}
    }

}

interface Search {
    fun searchByTitle(t : String):List<Book>
    fun searchByAuthor(a : String):List<Book>
    fun searchBySubject(s : String):List<Book>
    fun searchByPubDate(d : LocalDateTime):List<Book>
}

class Catelog : Search {
    private var bookTitles = mutableMapOf<String,MutableList<Book>>()
    private var bookAuthors = mutableMapOf<String,MutableList<Book>>()
    private var bookSubjects = mutableMapOf<String,MutableList<Book>>()
    private var bookPublicationDates = mutableMapOf<String,MutableList<Book>>()

    override fun searchByTitle(t: String) = bookTitles.getValue(t)
    override fun searchByAuthor(a: String) = bookAuthors.getValue(a)
    override fun searchBySubject(s: String)  = listOf<Book>()
    override fun searchByPubDate(d: LocalDateTime)  = listOf<Book>()
}