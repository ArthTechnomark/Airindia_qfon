package com.example.airindia_qfon.Utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
 import android.view.View
 import android.widget.TextView
import android.widget.Toast
import com.example.airindia_qfon.R


class Utils {
    fun isNetworkAvailable(activity: Context): Boolean {
        val connectivity =
            activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity == null) {
            return false
        } else {
            val info = connectivity.allNetworkInfo
            if (info != null) {
                for (anInfo in info) {
                    if (anInfo.state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
                }
            }
        }
        return false
    }

    fun showToastShortForNoInternet(_activity: Activity?) {
        if (_activity != null) {
            val mylf = _activity.layoutInflater
            val myview: View = mylf.inflate(R.layout.toast_layout, null)
            val text_data = myview.findViewById<TextView>(R.id.toast_text)
            text_data.text = "Please Check your Internet Connection"
            val mytoast = Toast(_activity)
            mytoast.duration = Toast.LENGTH_SHORT
            mytoast.setView(myview)
            if (!text_data.text.toString().isEmpty()) {
                mytoast.show()
            }
        }
    }





    fun islogin(_activity: Activity?):Boolean{
        val main_key = "my_pref"
        val login_key = "is_login"
        val preferences: SharedPreferences = _activity!!.getSharedPreferences(
            main_key,
            Context.MODE_PRIVATE
        )
        val sherdislogin = preferences.getString(login_key, "afd")

        if (sherdislogin=="1"){
            return true

        }
        return false

    }


}