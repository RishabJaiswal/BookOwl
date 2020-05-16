package com.gutenberg.bookowl.view.genre

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gutenberg.bookowl.R
import com.gutenberg.bookowl.data.models.Genre
import kotlinx.android.synthetic.main.item_genre.view.*

class GenreListAdapter(
    private val genreList: List<Genre>
) : RecyclerView.Adapter<GenreListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_genre, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(genreList[position])
    }

    override fun getItemCount(): Int {
        return genreList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(genre: Genre) {
            itemView.apply {
                tv_genre_title.text = genre.title
                imv_genre_icon.setImageResource(genre.icon)
            }
        }
    }
}