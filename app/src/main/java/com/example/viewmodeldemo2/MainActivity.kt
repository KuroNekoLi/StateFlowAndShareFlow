package com.example.viewmodeldemo2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.viewmodeldemo2.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var viewModelFactory: MainActivityViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModelFactory = MainActivityViewModelFactory(125)
        viewModel = ViewModelProvider(this,viewModelFactory).get(MainActivityViewModel::class.java)

        //collect是一個懸掛函數，所以必須要在Coroutine使用它
        //負責更新MutableStateFlow的類或代碼部分是生產者
        //而從StateFlow中收集的所有類都是消費者。
        //與使用flow建構器構建的冷流不同，StateFlow是一個熱流。
        lifecycleScope.launchWhenCreated {
            viewModel.floTotal.collect{
                binding.resultTextView.text = it.toString()
            }
        }
        lifecycleScope.launchWhenCreated{
            viewModel.message.collect{
                Toast.makeText(application,it,Toast.LENGTH_SHORT).show()
            }
        }
        binding.insertButton.setOnClickListener {
            viewModel.setTotal(binding.inputEditText.text.toString().toInt())
        }
    }
}