package com.eunidev.filtersearchviewytp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class RVAdapter(private val context: Context, list: ArrayList<Content>) : RecyclerView.Adapter<RVAdapter.ViewHolder>(), Filterable {

    private val mainList = list
    private val searchList = ArrayList<Content>(list)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.textViewTitle_CardView)
        val tvDescription = itemView.findViewById<TextView>(R.id.textViewDescription_CardView)

        fun bind(content: Content) {
            tvTitle.text = content.title
            tvDescription.text = content.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_view, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = mainList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = mainList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val filteredList = ArrayList<Content>()

                if (constraint.isBlank() or constraint.isEmpty()) {
                    filteredList.addAll(searchList)
                } else {
                    val filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim()

                    searchList.forEach {
                        if (it.title.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                            filteredList.add(it)
                        }
                    }
                }

                val result = FilterResults()
                result.values = filteredList

                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                mainList.clear()
                mainList.addAll(results!!.values as List<Content>)
                notifyDataSetChanged()
            }
        }
    }
}