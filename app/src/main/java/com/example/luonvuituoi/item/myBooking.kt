package com.example.luonvuituoi.item

class myBooking {
    var nameMall: String? = null
    var boxParking: String? = null
    var timeBook: String? = null
    var timeLeft: String? = null
    var paymentFee: String? = null
    var paymentType: String? = null



    constructor()
    constructor(
        nameMall: String?,
        boxParking: String?,
        timeBook: String?,
        timeLeft: String?,
        paymentFee: String?,
        paymentType: String?
    ) {
        this.nameMall = nameMall
        this.boxParking = boxParking
        this.timeBook = timeBook
        this.timeLeft = timeLeft
        this.paymentFee = paymentFee
        this.paymentType = paymentType
    }
}