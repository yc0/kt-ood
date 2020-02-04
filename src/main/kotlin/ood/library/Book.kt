package ood.library

import java.lang.Exception
import java.time.LocalDateTime

open class Book (t:String){
    var ISBN : String? = null
    var title : String? = t
        private set
    var subject : String? = null
    var publisher : String? =  null
    var lang  : String? = null
    var numberOfPages : Int? = null
}

class BookItem(t:String) : Book (t) {
    var barcode : String? = null
    var isReferenceOnly : Boolean? = null
    var borrowed : LocalDateTime? = null
    var price : Double? = null
    var dueDate : LocalDateTime?  = null
    var status : BookStatus? = null
    var format : BookFormat? = null
    var dateOfPurchase : LocalDateTime? = null
    var publicationDate : LocalDateTime? = null
    var placedAt : Rack? = null
    var isIssuable : Boolean = true
    fun checkout(memberID : String) : Boolean {
        if(!isIssuable) {
            println("This book is Reference only and can't be issued")
            return false
        }
        if(!BookLending.lendBook(this.barcode!!, memberID)) {
            return false
        }
        this.status = BookStatus.LOANED
        return true
    }
}

data class Rack(var number:Int, var locationID:String)
