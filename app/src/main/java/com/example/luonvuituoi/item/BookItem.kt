package com.example.luonvuituoi.item

class BookItem {
    var car: Int? = null
    var id: String? = null
    var time: String? = null
    var user_id: String? = null
    var available: String? = null

    constructor(car: Int?, id: String?, time: String?, user_id: String?, available: String?) {
        this.car = car
        this.id = id
        this.time = time
        this.user_id = user_id
        this.available = available
    }
}