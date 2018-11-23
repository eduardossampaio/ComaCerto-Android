package apps.esampaio.com.comacerto.core.entity

import android.content.Intent

import java.util.Date

class Water {
    var dateAndTime: Date
    var quantity: Int

    constructor(dateAndTime: Date, quantity: Int) {
        this.dateAndTime = dateAndTime
        this.quantity = quantity
    }
}
