package com.eyyuperdogan.countrieslist.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.eyyuperdogan.countrieslist.R

//Extension
//örnek olsun diye yaptım!!
/*
fun String.myExtension(myParameter:String){
    println(myParameter)
}

 */

fun ImageView.downloadFromUrl(url:String,progressDrawable: CircularProgressDrawable){
    //görsel gelmez ise ne yapalım
    val option=RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)

    Glide.with(context)
        .setDefaultRequestOptions(option)
        .load(url)
        .into(this)
}
//görsel gelmez ise image progresbar göstericem
fun placeHolderPrrogresBar(context:Context):CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth=8f
        centerRadius=40f
        start()
    }

}

@BindingAdapter("android:downloadUrl")
fun downloadİmage(view:ImageView,url: String?){
    view.downloadFromUrl(url.toString(), placeHolderPrrogresBar(view.context))
}