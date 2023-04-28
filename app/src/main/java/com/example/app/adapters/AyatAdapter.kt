package com.example.app.adapters

import com.example.app.R

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.app.model.AyatData

class AayatAdapter(private val aayatList: ArrayList<AyatData>):
    RecyclerView.Adapter<AayatAdapter.ViewHolder>() {

    private var context:Context? = null

    init {
        this.context= context
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ayat_arb:TextView = itemView.findViewById(R.id.arabic_text)
        val ayatNo:TextView = itemView.findViewById(R.id.ayatNo)
        val translation:TextView = itemView.findViewById(R.id.translation_text)
        val transliteration:TextView = itemView.findViewById(R.id.roman_text)
        val bookMark:ImageView = itemView.findViewById(R.id.bookmark)
        val parent:ConstraintLayout = itemView.findViewById(R.id.parent)
        val layout:LinearLayout = itemView.findViewById(R.id.linearLayout2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_design,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return aayatList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ayat_arb.text = aayatList[position].arabicText
        holder.ayatNo.text = aayatList[position].ayaNo
        holder.translation.text = aayatList[position].fatehMuhammad
        holder.transliteration.text = aayatList[position].muftiTaqi
        holder.itemView.setOnLongClickListener{
            holder.layout.visibility=View.VISIBLE
            true
        }
    }
}
