package com.example.searchapp.view

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.airtelapp.view_model.HomeViewModel
import com.example.searchapp.R
import com.example.searchapp.adapter.AddressListAdapter
import com.example.searchapp.data.AddressResponse
import com.example.searchapp.listener.MessageEvent
import com.example.searchapp.utils.gone
import com.example.searchapp.utils.showAsToast
import com.example.searchapp.utils.visible
import kotlinx.android.synthetic.main.activity_home.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class HomeActivity : AppCompatActivity() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: AddressListAdapter
    private lateinit var dialog: Dialog

    val TAG = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        setContentView(R.layout.activity_home)
        initViews()
        initDialog()
    }

    override fun onStart() {
        Log.i(TAG, "onStart")
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    private fun initViews() {
        Log.i(TAG, "initViews")
        adapter = AddressListAdapter()
        recyclerView.adapter = adapter

        search_tv.setOnClickListener {
            showDialog()
        }
    }

    private fun getData(query: String, city: String) {
        Log.i(TAG, "getData")
        search_tv.gone()
        homeViewModel.getResponse(query, city).observe(this, Observer { data ->
            progress.gone()
            if (data != null) {
                if (data.responseBody is AddressResponse) {
                    val address = data.responseBody as AddressResponse
                    adapter.run {
                        addressList.clear()
                        if (address.data != null && address.data.addressList.isNotEmpty()) {
                            empty_tv.gone()
                            addressList.addAll(address.data.addressList)
                        } else {
                            empty_tv.visible()
                        }
                        notifyDataSetChanged()

                    }
                }
            }
        })
    }

    private fun initDialog() {
        dialog = Dialog(this, android.R.style.Theme_DeviceDefault_Dialog)
        dialog.setContentView(R.layout.dailog_search)

        val search = dialog.findViewById<EditText>(R.id.search_et)
        val city = dialog.findViewById<EditText>(R.id.city_et)
        val searchBtn = dialog.findViewById<Button>(R.id.search_btn)

        searchBtn.setOnClickListener {
            if (validateData(search.text.trim().toString(), city.text.trim().toString())) {
                progress.visible()
                getData(search.text.toString(), city.text.toString())
                search.text.clear()
                city.text.clear()
                dialog.dismiss()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (::dialog.isInitialized && dialog.isShowing)
            dialog.dismiss()

    }

    private fun showDialog() {
        if (::dialog.isInitialized)
            dialog.show()
    }

    private fun validateData(search: String, city: String): Boolean {
        return if (search.isBlank() || search.isEmpty()) {
            "Please enter search".showAsToast()
            false
        } else if (city.isBlank() || city.isEmpty()) {
            "Please enter city".showAsToast()
            false
        } else {
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_search -> showDialog()
        }
        return false
    }

    @Subscribe
    fun onEvent(event: MessageEvent) {
        progress.gone()
        event.message.showAsToast()
    }

    companion object {
        fun initIntent(context: Context) {
            context.startActivity(Intent(context, HomeActivity::class.java))
        }
    }
}