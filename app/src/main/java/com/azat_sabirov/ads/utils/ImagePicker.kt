package com.azat_sabirov.ads.utils

import androidx.appcompat.app.AppCompatActivity
import com.fxn.pix.Options
import com.fxn.pix.Pix

object ImagePicker {
    const val REQUEST_COD_GET_IMAGES = 999
    fun getImages(context: AppCompatActivity, imageCounter: Int) {
        val options = Options.init()
            .setRequestCode(REQUEST_COD_GET_IMAGES)                        //Request code for activity results
            .setCount(imageCounter)                                                   //Number of images to restict selection count
            .setFrontfacing(false)                                         //Front Facing camera on start
            .setMode(Options.Mode.Picture)                                     //Option to select only pictures
            .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)     //Orientation
            .setPath("/pix/images")                                        //Custom Path For media Storage

        Pix.start(context, options);
    }
}