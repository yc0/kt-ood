package ood.library

import java.lang.Exception
import java.time.Duration
import java.time.LocalDateTime
import java.time.Period

abstract class Account {
    var id : String? = null
    var password : String? = null
    var status : AccountStatus = AccountStatus.NONE
    var person : Person? = null
    abstract fun resetPassword(): Boolean
}

abstract class Librarian : Account () {
    override fun resetPassword(): Boolean = true
    abstract fun addBookItem(bookItem: BookItem?) : Boolean
    fun blockMember(memberid: String) : Boolean {
        if(blocks.contains(memberid)) return false
        blocks.add(memberid)
        return true
    }
    fun unblockMember(memberid: String) :Boolean {
        if(!blocks.contains(memberid)) return false
        blocks.remove(memberid)
        return true
    }
    companion object {
        var blocks = mutableSetOf<String>()
    }
}

class Member : Account () {
    var dateOfMembership : LocalDateTime = LocalDateTime.now()
    private var totalBooksCheckedout : Int = 0
    override fun resetPassword(): Boolean  = true

    fun getTotalBooksCheckout() : Int  = totalBooksCheckedout
    fun reserveBookItem(bi : BookItem) : Boolean = BookReservation.makeReservation(bi.barcode!!, id!!)
    fun checkoutBookItem(bi : BookItem) : Boolean {
        if(getTotalBooksCheckout() >= Constants.MAX_BOOKS_ISSUED_TO_A_USER) {
            println("The user has already checked-out maximum number of books")
            return false
        }
        var br = BookReservation.fetchReservationDetails(bi.barcode!!)
        if(br != null) {
            if(br.memberid != id) {
                println("This book is reserved by another member")
                return false
            }
            bi.status = BookStatus.RESERVED
            br.status = ReservationStatus.COMPLETED
        }
        if(!bi.checkout(id!!)) return false
        ++totalBooksCheckedout
        return true
    }

    fun checkForFine(barcode : String) : Unit {
        var bl = BookLending.fetchLendingDetails(barcode)
        var from = bl?.dueDate
        var to = LocalDateTime.now()
        var diff = Duration.between(from,to).toDays()
        Fine.collectFine(id, diff)
    }

    fun returnBookItem(bi : BookItem) : Unit {
        checkForFine(bi.barcode!!)
        bi.status = BookStatus.AVAILABLE
        --totalBooksCheckedout

        BookLending.unlendBook(bi.barcode!!)
        var br = BookReservation.fetchReservationDetails(bi.barcode!!)
        if(br != null) {
            bi.status = BookStatus.RESERVED
            BookReservation.sendBookAvailabileNotificaton()
        }
    }

    fun renewBookItem( bi :BookItem) : Boolean {
        checkForFine(bi.barcode!!)
        var br = BookReservation.fetchReservationDetails(bi.barcode!!)
        if(br != null && br.memberid != id) {
            println("This book is reserved by another member")
            BookLending.unlendBook(bi.barcode!!)

            bi.status = BookStatus.RESERVED
            BookReservation.sendBookAvailabileNotificaton()
            return false
        } else if (br != null) {
            br.status = ReservationStatus.COMPLETED
        }

        BookLending.unlendBook(bi.barcode!!)
        return BookLending.lendBook(bi.barcode!!, id!!)
    }
}
//
//abstract class Librarian: Account() {
////    fun addBookItem(bookItem: BookItem?): Boolean
//    fun blockMember(member: Member?): Boolean
//    fun unBlockMember(member: Member?): Boolean
//}
