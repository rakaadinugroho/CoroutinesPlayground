package com.rakaadinugroho.coroutinesplayground.ui.search

import androidx.lifecycle.*
import com.rakaadinugroho.coroutinesplayground.data.DocumentResponse
import com.rakaadinugroho.coroutinesplayground.network.PlosService
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SearchVM(val plosService: PlosService): ViewModel() {
    val keyword = Channel<String>()
    val suggestions = MutableLiveData<List<DocumentResponse.Response.Doc>>()

    init {
        suggestionSearch()
    }

    fun getSuggestions(typeKeyword: String) {
        viewModelScope.launch(context = CoroutineName("suggestion-typer")) {
            keyword.send(typeKeyword)
        }
    }

    private fun suggestionSearch() {
        viewModelScope.launch(context = CoroutineName("suggestion-search")) {
            keyword.consumeAsFlow()
                .map { keyword ->
                    plosService.searchDocument(keyword)
                }
                .collect { documentResponse ->
                    suggestions.postValue(documentResponse.response.docs)
                }
        }
    }

    fun searchDocuments(keyword: String): LiveData<List<DocumentResponse.Response.Doc>> = liveData {
        val data = plosService.searchDocument(keyword = keyword)
        emit(data.response.docs)
    }
}