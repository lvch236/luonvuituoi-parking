package com.example.luonvuituoi.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.luonvuituoi.adapter.HotNewAdapter
import com.example.luonvuituoi.databinding.FragmentHotNewsBinding
import com.example.luonvuituoi.viewModel.HotNewVM

class HotNewFragment : Fragment() {

    lateinit var binding: FragmentHotNewsBinding
    lateinit var vm: HotNewVM
    lateinit var adapter: HotNewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHotNewsBinding.inflate(inflater, container, false)
        vm = ViewModelProvider(this).get(HotNewVM::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpHotNewList()
        registerHotNewList()
        registerErrorList()
    }

    override fun onStart() {
        super.onStart()
        vm.getNowPlaying()
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