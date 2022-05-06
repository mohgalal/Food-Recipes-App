package com.example.foodapp.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.example.foodapp.R

class RecipesRowBinding {


    // here I did CustomAttributes for my views
    companion object {

        @BindingAdapter("downloadImageFromUrl")
        @JvmStatic
        fun downloadImageFromUrl(imageView: ImageView, url:String){

            imageView.load(url){
                crossfade(300)
                error(R.drawable.ic_error_terrain)
            }
        }

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes:Int){
            textView.text =likes.toString()
        }

        @BindingAdapter("setNumberOfMinutes")
        @JvmStatic
        fun setNumberOfMinutes(textView: TextView, minutes:Int){
            textView.text =minutes.toString()
        }

        @BindingAdapter("setVeganColor")
        @JvmStatic
        fun setVeganColor(view: View, vegan:Boolean){

            if (vegan){
                when(view){
                    is TextView -> {
                        view.setTextColor(ContextCompat.getColor(view.context, R.color.green))
                    }
                    is ImageView -> {
                        view.setColorFilter(ContextCompat.getColor(view.context, R.color.green))
                    }
                }
            }
        }

    }




}