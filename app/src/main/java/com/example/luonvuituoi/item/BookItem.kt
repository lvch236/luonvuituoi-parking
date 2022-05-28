package com.example.luonvuituoi.item

class BookItem {
    var car: Int? = null
    var id: String? = null
    var time: Long? = null
    var user_id: String? = null
    var available: Boolean? = false

    constructor(car: Int?, id: String?, time: Long?, user_id: String?, available: Boolean?) {
        this.car = car
        this.id = id
        this.time = time
        this.user_id = user_id
        this.available = available
    }
}