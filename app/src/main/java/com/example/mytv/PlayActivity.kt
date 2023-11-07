package com.example.mytv

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.media3.exoplayer.ExoPlayer
import com.example.mytv.databinding.ActivityPlayBinding

class PlayActivity : FragmentActivity() {

    private val viewModel: PlayViewModel by lazy {
        ViewModelProvider(this).get(PlayViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityPlayBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_play)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val player = ExoPlayer.Builder(this).build()
        viewModel.setMyPlayer(player)
        binding.playerView.player = player

        binding.playerView.findViewById<View>(androidx.media3.ui.R.id.exo_settings).visibility =
            View.GONE
        binding.playerView.findViewById<View>(androidx.media3.ui.R.id.exo_rew_with_amount).visibility =
            View.GONE
        binding.playerView.findViewById<View>(androidx.media3.ui.R.id.exo_ffwd_with_amount).visibility =
            View.GONE
    }
}