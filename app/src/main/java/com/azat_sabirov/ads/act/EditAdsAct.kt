package com.azat_sabirov.ads.act

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.sax.RootElement
import android.widget.ArrayAdapter
import com.azat_sabirov.ads.R
import com.azat_sabirov.ads.databinding.ActivityEditAdsBinding
import com.azat_sabirov.ads.utils.CityHelper

class EditAdsAct : AppCompatActivity() {
    private lateinit var rootElement: ActivityEditAdsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(rootElement.root)

        val adapter = ArrayAdapter(
            this,android.R.layout.simple_spinner_item,CityHelper.getAllCountries(this))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        rootElement.spCountry.adapter = adapter
    }
}