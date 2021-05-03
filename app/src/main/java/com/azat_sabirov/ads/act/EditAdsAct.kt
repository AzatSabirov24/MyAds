package com.azat_sabirov.ads.act

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.azat_sabirov.ads.R
import com.azat_sabirov.ads.databinding.ActivityEditAdsBinding
import com.azat_sabirov.ads.dialogs.DialogSpinnerHelper
import com.azat_sabirov.ads.frag.FragmentCloseInterface
import com.azat_sabirov.ads.frag.ImageListFrag
import com.azat_sabirov.ads.utils.CityHelper
import com.azat_sabirov.ads.utils.ImagePicker
import com.fxn.pix.Pix
import com.fxn.utility.PermUtil


class EditAdsAct : AppCompatActivity(), FragmentCloseInterface {
    lateinit var rootElement: ActivityEditAdsBinding
    private val dialog = DialogSpinnerHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(rootElement.root)
        init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == ImagePicker.REQUEST_COD_GET_IMAGES) {
            if (data != null) {
                val returnValues = data.getStringArrayListExtra(Pix.IMAGE_RESULTS)
                if (returnValues?.size!! > 1) {
                    rootElement.svMain.visibility = View.GONE
                    val fm = supportFragmentManager.beginTransaction()
                    fm.replace(R.id.place_holder, ImageListFrag(this, returnValues))
                    fm.commit()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ImagePicker.getImages(this, 3)

                } else {
                    Toast.makeText(
                        this,
                        "Approve permissions to open Pix ImagePicker",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }
        }
    }

    private fun init() {
    }

    //OnCLicks

    fun onClickSelectCountry(view: View) {
        val listCountry = CityHelper.getAllCountries(this)
        dialog.showSpinnerDialog(this, listCountry, rootElement.tvCountry)
        if (rootElement.tvCountry.text != getString(R.string.select_city)) {
            rootElement.tvCity.text = getString(R.string.select_city)
        }
    }

    fun onClickSelectCity(view: View) {
        val selectCountry = rootElement.tvCountry.text.toString()
        if (selectCountry != getString(R.string.select_country)) {
            val listCity = CityHelper.getAllCities(selectCountry, this)
            dialog.showSpinnerDialog(this, listCity, rootElement.tvCity)
        } else {
            Toast.makeText(this, getString(R.string.select_country), Toast.LENGTH_LONG).show()
        }
    }

    fun onClickGetImages(view: View) {
        ImagePicker.getImages(this, 3)
    }

    override fun onFragClose() {
        rootElement.svMain.visibility = View.VISIBLE
    }
}