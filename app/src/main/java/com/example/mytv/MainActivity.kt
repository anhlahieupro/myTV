package com.example.mytv

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mytv.databinding.ActivityMainBinding

class MainActivity : FragmentActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.setContext(this)
    }
}