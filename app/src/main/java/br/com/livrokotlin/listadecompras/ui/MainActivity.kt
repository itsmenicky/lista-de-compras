package br.com.livrokotlin.listadecompras.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.livrokotlin.listadecompras.R
import br.com.livrokotlin.listadecompras.viewmodel.ItemsViewModel
import br.com.livrokotlin.listadecompras.model.ItemModel
import br.com.livrokotlin.listadecompras.viewmodel.ItemsViewModelFactory

class MainActivity : AppCompatActivity() {

    val viewModel: ItemsViewModel by viewModels{
        ItemsViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView);
        val itemsAdapter = ItemsAdapter { item ->
            viewModel.removeItem(item)
        }
        recyclerView.adapter = itemsAdapter

        viewModel.itemsLiveData.observe(this){ items ->
            itemsAdapter.updateItems(items)
        }

        val button = findViewById<Button>(R.id.button)
        val editText = findViewById<EditText>(R.id.edit_text)
        button.setOnClickListener {

            if (editText.text.isEmpty()) {
                editText.error = "Por favor, insira um valor!"
                return@setOnClickListener
            }

            viewModel.addItem(editText.text.toString())
            editText.text.clear()


            }
        }
    }