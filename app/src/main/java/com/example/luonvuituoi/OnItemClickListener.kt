package com.example.luonvuituoi

import com.example.luonvuituoi.item.MallItem


interface OnItemClickListener {

    fun onItemClicked(mallItem: MallItem)

    fun onDirectionsClicked(mallItem: MallItem)

}