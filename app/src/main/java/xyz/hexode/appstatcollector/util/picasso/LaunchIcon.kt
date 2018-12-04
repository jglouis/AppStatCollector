package xyz.hexode.appstatcollector.util.picasso

import android.net.Uri
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

fun ImageView.updateLaunchIcon(uri: Uri) {
    Picasso.get()
        .load(uri)
        .into(this, object : Callback {
            override fun onSuccess() = Unit
            override fun onError(e: Exception?) {
                setImageURI(uri)
            }
        })
}
