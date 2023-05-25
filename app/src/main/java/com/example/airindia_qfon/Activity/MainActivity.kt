package com.example.airindia_qfon.Activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.airindia_qfon.ApiHelper.Retroclient
import com.example.airindia_qfon.Modelclass.Root
import com.example.airindia_qfon.R
import com.test.mymadical.Interface.AllDataInterFace
import com.test.mymadical.Interface.ClickInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getcategory()
    }

    private fun getcategory() {


        val creation: AllDataInterFace =
            Retroclient.getSingleTonClient()!!.create(AllDataInterFace::class.java)
        val call = creation.insertdata("1", "5")
        Log.d("DSFSF", "SFSLODJKSI1U")


        call.enqueue(object : Callback<Root> {
            override fun onResponse(call: Call<Root>, response: Response<Root>) {

                if (response.isSuccessful) {
                    Log.e("asd", "   count  " + response.body()?.data?.size)
                } else {
                    Log.e("asd", "   count  " + response.message())

                }

            }

            override fun onFailure(call: Call<Root>, t: Throwable) {
                Log.e("DSFSF", t.message + " hii")

            }

        })


    }


}