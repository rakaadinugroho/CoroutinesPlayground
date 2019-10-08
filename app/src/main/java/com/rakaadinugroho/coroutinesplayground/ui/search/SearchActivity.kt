package com.rakaadinugroho.coroutinesplayground.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.rakaadinugroho.coroutinesplayground.R
import com.rakaadinugroho.coroutinesplayground.data.DocumentResponse
import com.rakaadinugroho.coroutinesplayground.network.NetworkProvider
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    private lateinit var searchItem: SearchView
    private lateinit var viewModel: SearchVM
    private val searchAdapter by lazy { SearchAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        viewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SearchVM(NetworkProvider.providePlosClient()) as T
            }
        })[SearchVM::class.java]

        viewModel.suggestions.observe(this, Observer { result ->
            Log.d(this@SearchActivity::class.java.simpleName,
                Gson().toJson(result))
            handledData(result)
        })

        with(document_list) {
            val linearLayoutManager = LinearLayoutManager(this@SearchActivity)
            layoutManager = linearLayoutManager
            adapter = searchAdapter
            addItemDecoration(DividerItemDecoration(this@SearchActivity, linearLayoutManager.orientation))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val search = menu.findItem(R.id.search_item)
        searchItem = search.actionView as SearchView

        searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.searchDocuments(query).observe(this@SearchActivity, Observer { result ->
                        Log.d(this@SearchActivity::class.java.simpleName,
                            "live-data : ${Gson().toJson(result)}")
                        handledData(result)
                    })
                }
                searchItem.onActionViewCollapsed()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    viewModel.getSuggestions(newText)
                }
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)

    }

    private fun handledData(result: List<DocumentResponse.Response.Doc>) {
        if (result.isNotEmpty())
            searchAdapter.listOfDocs = result
    }
}
