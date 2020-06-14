package com.user.tvh.services

import android.graphics.BitmapFactory
import androidx.compose.Composable
import androidx.compose.onActive
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.graphics.ImageAsset
import androidx.ui.graphics.asImageAsset
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.wrapContentSize
import androidx.ui.material.CircularProgressIndicator
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class ImageLoader(private val executor: AppExecutor) {

    private fun getImage(url: String, callback: (ImageAsset?) -> Unit) {
        executor.run({
            var stream: InputStream? = null
            try {
                val urlConn = URL(url).openConnection()
                val httpConn = urlConn as HttpURLConnection
                httpConn.connect()
                stream = httpConn.inputStream
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (stream != null) BitmapFactory.decodeStream(stream).asImageAsset() 
            else null
        }) {
            callback(it)
        }
    }


    @Composable
    fun UrlImage(
        url: String,
        children: @Composable() (ImageAsset) -> Unit
    ) {
        val (image: ImageAsset, setImage) = state {
            ImageAsset(1,1)
        }
        onActive {
            getImage(url) { imageAsset: ImageAsset? ->
                if (imageAsset != null) {
                    setImage(imageAsset)
                }
            }
        }

        if (image.width == 1) {
            Box(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
                CircularProgressIndicator()
            }
        } else {
            children(image)
        }
    }

}