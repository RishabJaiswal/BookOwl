package com.gutenberg.bookowl.view.books

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gutenberg.bookowl.R
import com.gutenberg.bookowl.application.utils.ImageLoader
import com.gutenberg.bookowl.data.models.Book
import kotlinx.android.synthetic.main.item_book.view.*

class BooksAdapter(private val onBookClicked: (book: Book) -> Unit) :
    ListAdapter<Book, BooksAdapter.BookViewHolder>(BookDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BookViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
    )

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun swapData(data: List<Book>) {
        submitList(data.toMutableList())
    }

    /**
     * view holder for books item
     * */
    inner class BookViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), OnClickListener {

        init {
            itemView.setOnClickListener(this)
            itemView.imv_book_cover.clipToOutline = true
        }

        override fun onClick(v: View?) {
            if (adapterPosition == RecyclerView.NO_POSITION) return
            val selectedBook = getItem(adapterPosition)
            onBookClicked(selectedBook)
        }

        fun bind(book: Book) = with(itemView) {
            tv_book_title.text = book.title

            //setting author name
            tv_book_author.text = if (book.authors.isNotEmpty()) {
                book.authors[0].name
            } else {
                ""
            }

            //setting book cover
            ImageLoader.loadUrl(imv_book_cover, book.bookFormat.bookCoverUrl)
        }
    }

    interface Interaction {}

    /**
     * This class propagates changes to the recycler view
     * for a given difference between old and new data
     * */
    private class BookDiffCallback : DiffUtil.ItemCallback<Book>() {

        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }
}