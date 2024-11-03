package br.com.livrokotlin.listadecompras.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.livrokotlin.listadecompras.R
import br.com.livrokotlin.listadecompras.model.ItemModel
import br.com.livrokotlin.listadecompras.model.toEntity
import kotlinx.coroutines.Dispatchers


class ItemsAdapter(private val onRemove: (ItemModel) -> Unit): RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {
    private val items = mutableListOf<ItemModel>()

    fun updateItems(newItems: List<ItemModel>){
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, onRemove)
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val textView = view.findViewById<TextView>(R.id.textViewItem)
        private val button = view.findViewById<ImageButton>(R.id.imageButton)
            fun bind(item: ItemModel, onRemove: (ItemModel)-> Unit) {
            textView.text = item.name

            button.setOnClickListener {
                onRemove(item)
            }
        }

    }
}