package br.com.livrokotlin.listadecompras.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.livrokotlin.listadecompras.data.ItemEntity
import br.com.livrokotlin.listadecompras.data.ItemsDatabase
import br.com.livrokotlin.listadecompras.data.toModel
import br.com.livrokotlin.listadecompras.model.ItemModel
import br.com.livrokotlin.listadecompras.model.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class ItemsViewModel(
    private val database: ItemsDatabase
) : ViewModel() {

    val itemsLiveData = MutableLiveData<List<ItemModel>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchAll()
        }
    }

    fun addItem(name: String) {
        viewModelScope.launch(Dispatchers.IO){
            val entity = ItemEntity(id = 0, name = name)
            database.itemsDao().insert(entity)
            fetchAll()
        }

    }

    fun removeItem(item: ItemModel) {
        viewModelScope.launch(Dispatchers.IO){
            database.itemsDao().delete(item.toEntity())
            fetchAll()
        }
    }

    private suspend fun fetchAll(){
        viewModelScope.launch(Dispatchers.IO){
            val result = database.itemsDao().getAll()
            val itemModels = result.map {it.toModel(onRemove = {
            })}
            itemsLiveData.postValue(itemModels)
        }

    }

}
