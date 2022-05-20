package com.example.luonvuituoi.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.Resource
import com.example.luonvuituoi.R
import com.example.luonvuituoi.adapter.HotFeedAdapter
import com.example.luonvuituoi.adapter.HotNewAdapter
import com.example.luonvuituoi.databinding.FragmentHotNewsBinding
import com.example.luonvuituoi.databinding.HotFeedItemLayoutBinding
import com.example.luonvuituoi.viewModel.HotNewVM

class HotNewFragment : Fragment() {

    lateinit var binding: FragmentHotNewsBinding
    lateinit var vm: HotNewVM
    lateinit var adapter: HotNewAdapter
    lateinit var adapter1 : HotFeedAdapter
    private lateinit var keyList : List<String>
    private var keyword = "All"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHotNewsBinding.inflate(inflater, container, false)
        vm = ViewModelProvider(this).get(HotNewVM::class.java)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpHotFeedList()
        setUpHotNewList()
        registerHotNewList()
        registerErrorList()
    }

    override fun onStart() {
        super.onStart()
        if (keyword != "All") vm.getNowPlaying(keyword)
        else vm.getNowPlaying("*")
    }

    private fun setUpHotFeedList() {
        keyList = listOf("All","Science","Life","Technology","Social","Economic")

        adapter1 = HotFeedAdapter(keyList, keyword)
        adapter1.setOnItemClickListener(object : HotFeedAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                keyword = keyList[position]
                if (keyword != "All") vm.getNowPlaying(keyword)
                else vm.getNowPlaying("*")
                setUpHotFeedList()
            }
        })
        val lm = LinearLayoutManager(context)
        lm.orientation = RecyclerView.HORIZONTAL
        binding.rcvFeedList.layoutManager = lm
        binding.rcvFeedList.adapter = adapter1

    }

    private fun setUpHotNewList() {
        adapter = HotNewAdapter()
        adapter.setOnItemClickListener(object : HotNewAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val url = vm.hotNewData.value?.get(position)?.url
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }

        })
        val lm = LinearLayoutManager(context)
        binding.rcvList.layoutManager = lm
        binding.rcvList.adapter = adapter
    }

    private fun registerHotNewList() {
        vm.hotNewData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun registerErrorList() {
        vm.errEvent.observe(viewLifecycleOwner){
            //show dialog
        }
    }
}