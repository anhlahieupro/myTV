package com.example.mytv

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private var context: Context? = null

    fun setContext(context: Context) {
        this.context = context
    }

    fun goToPlay() {
        val intent = Intent(context, PlayActivity::class.java)
        context?.startActivity(intent)
    }
}