package com.example.mviproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var numberTextView: TextView
    lateinit var addNumberBTN: Button
    private val viewModel: AddNumberViewModel by lazy {
        ViewModelProvider(this)[AddNumberViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberTextView = findViewById(R.id.number_TextView)
        addNumberBTN = findViewById(R.id.add_number_button)

        rendering()

        addNumberBTN.setOnClickListener {
            //Send
            lifecycleScope.launch {
                viewModel.intentChannel.send(MainIntent.AddNumber)
            }
        }
    }

    //Render
    private fun rendering() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                when(it) {
                    is MainViewState.Idle -> numberTextView.text = "Idle"
                    is MainViewState.Number -> numberTextView.text = it.number.toString()
                    is MainViewState.Error -> numberTextView.text = it.error
                }
            }
        }
    }
}