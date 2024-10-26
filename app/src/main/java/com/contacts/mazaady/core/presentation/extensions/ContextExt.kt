package com.contacts.mazaady.core.presentation.extensions

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import com.contacts.mazaady.R
import es.dmoral.toasty.Toasty

fun Context.showToast(message: String, connectionError: Boolean = false) {
    if (connectionError) {
        showErrorMessage(getString(R.string.check_internet_connections))
    } else {
        //Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        showErrorMessage(message)

    }
    Log.d("error", message)
}

fun Context.showGenericAlertDialog(
    parentFragment: FragmentManager? = null,
    code: Int = 0,
    message: String = ""
) {
    AlertDialog.Builder(this).apply {
        setMessage(message)
        setIcon(R.drawable.ic_mazaady)
        setTitle(getString(R.string.app_name))
        setPositiveButton(getString(R.string.ok)) { dialog, _ ->
            dialog.dismiss()
        }
    }.show()
}

fun Context.customDialogForAction(
    message: String = "",
    onAction:()->Unit
) {
    AlertDialog.Builder(this).apply {
        setMessage(message)
        setIcon(R.drawable.ic_mazaady)
        setTitle(getString(R.string.app_name))
        setPositiveButton(getString(R.string.ok)) { dialog, _ ->
            onAction()
            dialog.dismiss()
        }
        setNegativeButton(getString(R.string.no)) { dialog, _ ->
            dialog.dismiss()
        }
    }.show()
}

fun Context.loadFullScreenImg(url: String = "", imgUrl: List<String> = emptyList()) {
    val urls = ArrayList<String>()
    if (url.isNotEmpty()) {
        urls.add(url)
    } else {
        urls.addAll(imgUrl)
    }

//    StfalconImageViewer.Builder(this, urls) { imageView: ImageView?, image: String? ->
//        val with = Glide.with(this)
//        val requestBuilder: RequestBuilder<Drawable> = with.load(image)
//        requestBuilder.into(imageView!!)
//    }.withStartPosition(0)
//        .withImageChangeListener { position: Int -> println(position) }
//        .show()
}

fun Context.loadFullScreenImgSlider(position: Int = 0, imgUrl: List<String> = emptyList()) {
//    val urls = ArrayList<String>()
//    if(url.isNotEmpty()){
//        urls.add(url)
//    }else{
//        urls.addAll(imgUrl)
//    }

//    StfalconImageViewer.Builder(this, imgUrl) { imageView: ImageView?, image: String? ->
//        val with = Glide.with(this)
//        val requestBuilder: RequestBuilder<Drawable> = with.load(image)
//        requestBuilder.into(imageView!!)
//    }.withStartPosition(position)
//        .withImageChangeListener { position: Int -> println(position) }
//        .show()
}

fun Context.showSuccessMessage(message: String = "") {
    Toasty.success(this, message, Toast.LENGTH_SHORT, true).show()
}

fun Context.showErrorMessage(message: String = "") {
    Toasty.error(this, message, Toast.LENGTH_SHORT, true).show()
}

fun Context.showInfoMessage(message: String = "") {
    Toasty.info(this, message, Toast.LENGTH_SHORT, true).show()
}