package com.example.viewmodeldemo2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(startingTotal : Int) : ViewModel() {

    private val _flowTotal = MutableStateFlow<Int>(0)
    val floTotal : StateFlow<Int>
        get() = _flowTotal

    private val _message = MutableSharedFlow<String>()
    val message : SharedFlow<String>
        get() = _message

    init {
        _flowTotal.value = startingTotal
    }

    fun setTotal(input:Int){
        _flowTotal.value = (_flowTotal.value).plus(input)

        //emit a String message 因為使用懸掛函數emit，所以需要coroutine scope
        //因為在view model中，所以使用viewModelScope
        viewModelScope.launch {
            _message.emit("Total Update Successfully")
        }
    }
}