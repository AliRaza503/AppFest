package com.example.app.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.app.R
import com.example.app.adapters.AayatAdapter
import com.example.app.database.ReadDatabase
import com.example.app.databinding.FragmentAyatBinding
import com.example.app.model.AyatData

class AyatFragment : Fragment() {

    private lateinit var binding: FragmentAyatBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAyatBinding.inflate(inflater, container, false)
        if (arguments?.getString("dataType").equals("juz")){
            val ayaatList = ReadDatabase.getAyatDataList().filter { it.paraId == arguments?.getString("surahNo")}
            binding.recyclerview.adapter = AayatAdapter(ayaatList as ArrayList<AyatData>)
        }else {
            val ayaatList = ReadDatabase.getAyatDataList()
                .filter { it.suraId == arguments?.getString("surahNo") }
            binding.recyclerview.adapter = AayatAdapter(ayaatList as ArrayList<AyatData>)
        }

        return binding.root
    }

}