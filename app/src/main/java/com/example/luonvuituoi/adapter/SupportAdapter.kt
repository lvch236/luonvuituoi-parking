package com.example.luonvuituoi.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.luonvuituoi.R
import com.example.luonvuituoi.databinding.FragmentSupportBinding
import com.example.luonvuituoi.databinding.MessageItemBinding
import com.example.luonvuituoi.item.Message
import com.example.luonvuituoi.utils.Constants.RECEIVE_ID
import com.example.luonvuituoi.utils.Constants.SEND_ID


class SupportAdapter: RecyclerView.Adapter<SupportAdapter.MessageViewHolder>() {
    var messagesList = mutableListOf<Message>()
    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
           // Log.e("conghau","1234");
            itemView.setOnClickListener {

                //Remove message on the item clicked
                messagesList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
       // Log.e("conghau","1234");
        return MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
       // Log.e("conghau","1234");
        return messagesList.size
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
       // Log.e("conghau","1234");
        val currentMessage = messagesList[position]

        when (currentMessage.id) {
            SEND_ID -> {
                holder.itemView.findViewById<TextView>(R.id.tv_message).apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.itemView.findViewById<TextView>(R.id.tv_bot_message).visibility = View.GONE
            }
            RECEIVE_ID -> {
                holder.itemView.findViewById<TextView>(R.id.tv_bot_message).apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.itemView.findViewById<TextView>(R.id.tv_message).visibility = View.GONE
            }
        }
    }
    fun insertMessage(message: Message) {
       // Log.e("conghau","1234");
        this.messagesList.add(message)
        notifyItemInserted(messagesList.size)
    }

}