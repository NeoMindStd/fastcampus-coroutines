package kr.co.fastcampus.co.kr.coroutines.ui.main

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kr.co.fastcampus.co.kr.coroutines.model.Item

class FavoritesAdapter(
    private val like: (Item) -> Unit = {}
) : RecyclerView.Adapter<ImageSearchViewHolder>() {
    var items: List<Item> = emptyList()
        set(value) {
            field = value.toList()
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSearchViewHolder {
        return ImageSearchViewHolder.create(like, parent)
    }

    override fun onBindViewHolder(holder: ImageSearchViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size
}
