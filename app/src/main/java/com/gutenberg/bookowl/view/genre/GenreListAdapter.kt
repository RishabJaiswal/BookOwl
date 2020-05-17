package com.gutenberg.bookowl.view.genre

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gutenberg.bookowl.R
import com.gutenberg.bookowl.data.models.Genre
import kotlinx.android.synthetic.main.item_genre.view.*

class GenreListAdapter(
    private val genreList: List<Genre>,
    val onGenreSelected: (genre: Genre) -> Unit
) : RecyclerView.Adapter<GenreListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_genre, parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(genreList[position])
    }

    override fun getItemCount(): Int {
        return genreList.size
    }


    //View holder for Genre item
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private lateinit var genre: Genre

        init {
            itemView.container_genre.setOnClickListener(this)
        }

        fun bind(genre: Genre) {
            this.genre = genre
            itemView.apply {
                tv_genre_title.text = genre.title
                imv_genre_icon.setImageResource(genre.icon)
            }
        }

        override fun onClick(v: View?) {
            onGenreSelected(genre)
        }
    }
}