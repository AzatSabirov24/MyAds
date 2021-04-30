package com.azat_sabirov.ads.act

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

    fun onClickSelectCountry(view: View){
        val listCountry = CityHelper.getAllCountries(this)
        dialog.showSpinnerDialog(this, listCountry)
    }
}