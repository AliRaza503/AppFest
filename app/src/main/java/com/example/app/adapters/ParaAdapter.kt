package com.example.app.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R

class ParaAdapter(private val surah: ArrayList<Int>):
    RecyclerView.Adapter<ParaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val surahNum = itemView.findViewById<AppCompatTextView>(R.id.surahNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.surah_card,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.surahNum.text = surah[position].toString()
        holder.itemView.setOnClickListener {
            val bundle = bundleOf("surahNo" to surah[position].toString())
            holder.itemView.findNavController().navigate(R.id.action_nav_home_to_ayatFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return surah.size
    }
}