package com.example.luonvuituoi.item

class parking {
    var time: String? = null
    var user_id: String? = null
    var available: String? = null


    constructor(time: String?, user_id: String?, available: String?) {
        this.time = time
        this.user_id = user_id
        this.available = available
    }

    constructor()
}