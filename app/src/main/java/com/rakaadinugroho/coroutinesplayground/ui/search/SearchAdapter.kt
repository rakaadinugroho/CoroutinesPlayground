package com.rakaadinugroho.coroutinesplayground.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rakaadinugroho.coroutinesplayground.R
import com.rakaadinugroho.coroutinesplayground.data.DocumentResponse
import kotlinx.android.synthetic.main.document_item.view.*

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.SearchVH>() {
    private val differ = AsyncListDiffer<DocumentResponse.Response.Doc>(this,
        DiffCallback
    )
    var listOfDocs: List<DocumentResponse.Response.Doc> = emptyList()
        set(value) {
            field = value
            differ.submitList(value)
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVH {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.document_item, parent, false
        )
        return SearchVH(view)
    }

    override fun getItemCount(): Int = listOfDocs.size

    override fun onBindViewHolder(holder: SearchVH, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class SearchVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: DocumentResponse.Response.Doc) {
            itemView.title_document.text = data.titleDisplay
            itemView.type_document.text = data.articleType
        }
    }
}

object DiffCallback: DiffUtil.ItemCallback<DocumentResponse.Response.Doc>() {
    override fun areItemsTheSame(
        oldItem: DocumentResponse.Response.Doc,
        newItem: DocumentResponse.Response.Doc
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: DocumentResponse.Response.Doc,
        newItem: DocumentResponse.Response.Doc
    ): Boolean = oldItem.id == newItem.id
}