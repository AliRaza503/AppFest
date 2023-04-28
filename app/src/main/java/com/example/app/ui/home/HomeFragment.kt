package com.example.app.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.app.R
import com.example.app.adapters.AayatAdapter
import com.example.app.adapters.ParaAdapter
import com.example.app.database.ReadDatabase
import com.example.app.databinding.FragmentHomeBinding
import com.example.app.model.AyatData
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

//Reading Quran
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val suraList = ArrayList<Int>()
        //initialize suraList with numbers from 1 to 114
        var i = 0
        while (i < 114) {
            suraList.add(++i)
        }
        binding.recyclerview.adapter = ParaAdapter(suraList)
        return binding.root
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }