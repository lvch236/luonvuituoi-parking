package com.example.luonvuituoi.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luonvuituoi.R
import com.example.luonvuituoi.adapter.SupportAdapter
import com.example.luonvuituoi.databinding.FragmentSupportBinding
import com.example.luonvuituoi.item.Message
import com.example.luonvuituoi.utils.BotResponse
import com.example.luonvuituoi.utils.Constants.OPEN_GOOGLE
import com.example.luonvuituoi.utils.Constants.OPEN_SEARCH
import com.example.luonvuituoi.utils.Constants.RECEIVE_ID
import com.example.luonvuituoi.utils.Constants.SEND_ID
import com.example.luonvuituoi.utils.Time
import kotlinx.coroutines.*

class SupportFragment : Fragment() {
    private val botList = listOf("Peter", "Francesca", "Luigi", "Igor")
    lateinit var binding: FragmentSupportBinding
    private lateinit var adapter: SupportAdapter
    var messagesList = mutableListOf<Message>()
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentHotNewsBinding.inflate(inflater, container, false)
//        vm = ViewModelProvider(this).get(HotNewVM::class.java)
//        return binding.root
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSupportBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView()

        clickEvents()

        val random = (0..3).random()
        val namebot:String
        namebot = botList[random]
        Log.e("namebot",namebot)
        binding.userName.setText(namebot)
        customBotMessage("Hello! Today you're speaking with "+namebot+", how may I help?")
    }
    private fun recyclerView() {
        adapter = SupportAdapter()
        val lm = LinearLayoutManager(context)
        binding.rvMessages.adapter = adapter
        binding.rvMessages.layoutManager = lm

    }
    private fun clickEvents() {

        //Send a message
        binding.btnSend.setOnClickListener {
            sendMessage()
        }

        //Scroll back to correct position when user clicks on text view
        binding.textSend.setOnClickListener {
            GlobalScope.launch {
                delay(100)

                withContext(Dispatchers.Main) {
                    binding.rvMessages.scrollToPosition(adapter.itemCount - 1)

                }
            }
        }
    }
    private fun sendMessage() {
        val message = binding.textSend.text.toString()
        val timeStamp = Time.timeStamp()

        if (message.isNotEmpty()) {
            //Adds it to our local list
            messagesList.add(Message(message, SEND_ID, timeStamp))
            binding.textSend.setText("")

            adapter.insertMessage(Message(message, SEND_ID, timeStamp))
            binding.rvMessages.scrollToPosition(adapter.itemCount - 1)

            botResponse(message)
        }
    }
    private fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            //Fake response delay
            delay(1000)

            withContext(Dispatchers.Main) {
                //Gets the response
                val response = BotResponse.basicResponses(message)

                //Adds it to our local list
                messagesList.add(Message(response, RECEIVE_ID, timeStamp))

                //Inserts our message into the adapter
                adapter.insertMessage(Message(response, RECEIVE_ID, timeStamp))

                //Scrolls us to the position of the latest message
                binding.rvMessages.scrollToPosition(adapter.itemCount - 1)

                //Starts Google
                when (response) {
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfterLast("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }

                }
            }
        }
    }

    private fun customBotMessage(message: String) {
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val timeStamp = Time.timeStamp()
                messagesList.add(Message(message, RECEIVE_ID, timeStamp))
                adapter.insertMessage(Message(message, RECEIVE_ID, timeStamp))

                binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

}