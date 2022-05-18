package com.example.luonvuituoi.helper

import com.example.luonvuituoi.R
import com.example.luonvuituoi.item.MallItem

object DataStore {
    fun getDataSet(): List<MallItem> {
        return listOf(
                MallItem(R.drawable.dongkhoi, "VINCOM Dong Khoi, District 1", "9 km away", 56) ,
                    MallItem(R.drawable.scvivo, "SC VIVO, District 7", "15.5 km away", 107),
                    MallItem(R.drawable.crescent, "CRESCENT MALL, District 7", "9.5 km away", 54),
                    MallItem(R.drawable.takashima, "TAKASHIMAYA, District 1", "15 km away", 86),
                    MallItem(R.drawable.megamall, "VINCOM MEGA MALL, District 2", "14 km away", 42),
                    MallItem(R.drawable.pearl, "PEARL PLAZA, Binh Thanh District", "12 km away", 2),
                    MallItem(R.drawable.vanhanh, "Van Hanh Mall, District 10", "7 km away", 77),
                    MallItem(R.drawable.giga, "GIGA MALL, Thu Duc City", "3 km away", 99),
                    MallItem(R.drawable.bitexco, "BITEXCO, District 1", "10 km away", 188),

        )
    }
}