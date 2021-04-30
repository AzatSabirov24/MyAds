package com.azat_sabirov.ads.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.azat_sabirov.ads.R
import com.azat_sabirov.ads.utils.CityHelper

class DialogSpinnerHelper {
    fun showSpinnerDialog(context: Context, list: ArrayList<String>) {
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        val rootView = LayoutInflater.from(context).inflate(R.layout.spinner_layout, null)
        val adapter = RcViewDialogSpinnerAdapter(context,dialog)
        val rcView = rootView.findViewById<RecyclerView>(R.id.rcSpView)
        val sv = rootView.findViewById<SearchView>(R.id.svSpinner)
        rcView.layoutManager = LinearLayoutManager(context)
        rcView.adapter = adapter
        dialog.setView(rootView)
        adapter.updateAdapter(list)
        setSearchView(adapter, list, sv)
        dialog.show()
    }

    private fun setSearchView(
        adapter: RcViewDialogSpinnerAdapter,
        list: ArrayList<String>,
        sv: SearchView?
    ) {
        sv?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val tempList = CityHelper.filterListData(list, newText)
                adapter.updateAdapter(tempList)
                return true
            }
        })
    }
}