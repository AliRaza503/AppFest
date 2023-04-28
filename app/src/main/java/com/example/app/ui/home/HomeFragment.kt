package com.example.app.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.app.adapters.AayatAdapter
import com.example.app.database.ReadDatabase
import com.example.app.databinding.FragmentHomeBinding

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
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        ReadDatabase.initDatabase(requireContext())
        binding.recyclerview.adapter = ReadDatabase.ayatList?.let { AayatAdapter(it) }
        if (ReadDatabase.ayatList == null) {
            Log.d("Hello", "NULL")
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}