package com.example.mytv

import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageUrl")
fun loadImageFromUrl(view: ImageView, imageUrl: String?) {
    imageUrl?.let {
        Glide.with(view.context)
            .load(imageUrl)
            .apply(RequestOptions().centerCrop())
            .into(view)
    }
}

class PlayViewModel : ViewModel() {
    private val _image: MutableLiveData<String> = MutableLiveData("")
    val image: LiveData<String> = _image

    private val _hideImage: MutableLiveData<Boolean> = MutableLiveData(true)
    val hideImage: LiveData<Boolean> = _hideImage

    private val _hidePlayer: MutableLiveData<Boolean> = MutableLiveData(true)
    val hidePlayer: LiveData<Boolean> = _hidePlayer

    private var player: ExoPlayer? = null
    private val urls = mutableListOf<String>()

    fun setMyPlayer(player: ExoPlayer) {
        this.player = player

        addListener()
        getUrls()
        updateMediaItems()

        player.playWhenReady = true
        player.prepare()
        player.play()
    }

    private fun getUrls() {
        urls.addAll(
            listOf(
                "https://test-videos.co.uk/vids/bigbuckbunny/mp4/h264/1080/Big_Buck_Bunny_1080_10s_1MB.mp4",
                "https://play-lh.googleusercontent.com/HGfb2ClmDEA6xydDMJRQtQ5JfQcHiOJT2PKCBlNTkgJZv6igmrHHFmz4010OCq2Q8cY",
                "https://www.appsloveworld.com/wp-content/uploads/2018/10/640.mp4",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/2/28/HelloWorld.svg/2560px-HelloWorld.svg.png",
                "https://www.appsloveworld.com/wp-content/uploads/2018/10/Sample-Mp4-Videos.mp4"
            )
        )
    }

    private fun updateMediaItems() {
        for (url in urls) {
            if (url.contains("mp4")) {
                val mediaItem = MediaItem.Builder()
                    .setUri(url)
                    .setMediaId(url)
                    .setMimeType(MimeTypes.APPLICATION_MP4)
                    .build()

                player?.addMediaItem(mediaItem)
            }
        }
    }

    private fun addListener() {
        player?.addListener(object : Player.Listener {
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                val currentIndex = urls.indexOf(mediaItem?.mediaId)

                if (currentIndex == 0) {
                    showPlayer()
                    hideImage()
                    return
                }

                val beforeIndex = currentIndex - 1
                val beforeUrl = urls[beforeIndex]

                if (beforeUrl.contains("mp4")) {
                    showPlayer()
                    hideImage()
                    return
                }

                // before url is image
                player?.pause()
                hidePlayer()
                showImage(beforeUrl)

                Handler(Looper.getMainLooper()).postDelayed({
                    player?.play()
                    showPlayer()
                    hideImage()
                }, 5000)
            }
        })
    }

    private fun showImage(url: String) {
        _image.value = url
        _hideImage.value = false
    }

    private fun hideImage() {
        _image.value = ""
        _hideImage.value = true
    }

    private fun showPlayer() {
        _hidePlayer.value = false
    }

    private fun hidePlayer() {
        _hidePlayer.value = true
    }
}