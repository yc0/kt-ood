package ood.library

import java.time.LocalDateTime

class BookLending {
    var creationDate:LocalDateTime? = null
    var dueDate: LocalDateTime? = null
    var returnDate:LocalDateTime? = null
    var memberId : String? = null
    constructor()
    constructor(cd:LocalDateTime, dd:LocalDateTime, rd:LocalDateTime, mi:String)
    companion object {
        private var borrows  = mutableMapOf<String,BookLending>()
        fun lendBook(barcode: String, memberId: String) : Boolean {
            if(borrows.containsKey(barcode)) return false
            val today = LocalDateTime.now()
            val `return` = today.plusDays(Constants.MAX_RETURNING_DAYS.toLong())
            val due = today.plusDays(Constants.MAX_LENDING_DAYS.toLong())
            borrows[barcode] = BookLending(today, due, `return`, memberId)
            return true
        }
        fun unlendBook(barcode : String) : Unit {
            if(borrows.containsKey(barcode)) {
                borrows.remove(barcode)
            }
        }
        fun fetchLendingDetails(barcode: String) : BookLending? {
            if(!borrows.containsKey(barcode))
                return borrows[barcode]
            return null
        }
    }


}
//data class Fine(var createdAt:LocalDateTime,var  barcode:String, var memberId: String)
//object FineHandler {
//    var fines = mutableMapOf<String, Fine>()
//    fun collectFine(memberId:String, days : Long) : Unit = Unit
//}

class Fine {
    val creationDate: LocalDateTime? = null
    val bookItemBarcode = 0.0
    val memberId: String? = null

    companion object {
        fun collectFine(memberId: String?, days: Long) {}
    }
}