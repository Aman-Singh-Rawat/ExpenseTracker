package com.internship.expensetracker.presenter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.internship.expensetracker.R

class CustomSpinnerAdapter(context: Context, items: List<String>): ArrayAdapter<String>(context, 0, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent, R.layout.bg_not_selected_spinner)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent, R.layout.bg_spinner_item_selected)
    }

    private fun createViewFromResource(position: Int, convertView: View?, parent: ViewGroup, resource: Int): View {
        val view = convertView?: LayoutInflater.from(context)
            .inflate(resource, parent, false)

        val tvSpinner = view.findViewById<TextView>(R.id.tvSpinner)

        getItem(position)?.let {
            tvSpinner.text = it
        }
        return view
    }
}