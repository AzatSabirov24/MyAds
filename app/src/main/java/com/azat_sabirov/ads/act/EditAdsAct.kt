package com.azat_sabirov.ads.act

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.azat_sabirov.ads.R
import com.azat_sabirov.ads.databinding.ActivityEditAdsBinding
import com.azat_sabirov.ads.dialogs.DialogSpinnerHelper
import com.azat_sabirov.ads.utils.CityHelper

class EditAdsAct : AppCompatActivity() {
    lateinit var rootElement: ActivityEditAdsBinding
    private val dialog = DialogSpinnerHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(rootElement.root)
        init()
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
}