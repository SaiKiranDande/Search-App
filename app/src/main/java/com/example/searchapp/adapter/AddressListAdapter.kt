package com.example.searchapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.searchapp.R
import com.example.searchapp.data.AddressListItem
import com.example.searchapp.utils.gone
import com.example.searchapp.utils.visible
import kotlinx.android.synthetic.main.item_address_list.view.*

class AddressListAdapter : RecyclerView.Adapter<AddressListViewHolder>() {

    val addressList: MutableList<AddressListItem> = mutableListOf<AddressListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_address_list, parent, false)
        return AddressListViewHolder(view)
    }

    override fun getItemCount(): Int = addressList.size


    override fun onBindViewHolder(holder: AddressListViewHolder, position: Int) {
        holder.onBind(addressList[position])
    }
}

class AddressListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun onBind(addressListItem: AddressListItem): View = itemView.apply {
        item_address.text = addressListItem.addressString
        item_city.text = addressListItem.city
        item_fiber.text = addressListItem.label

        if (addressListItem.label == null || addressListItem.label.isEmpty())
            item_fiber_parent.gone()
        else
            item_fiber_parent.visible()
    }
}