package com.example.airindia_qfon.Activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.airindia_qfon.Adepter.AdepterAirlines
import com.example.airindia_qfon.ApiHelper.Retroclient
import com.example.airindia_qfon.Modelclass.Daum
import com.example.airindia_qfon.Modelclass.Root
import com.example.airindia_qfon.R
import com.example.airindia_qfon.Utils.Utils
import com.test.mymadical.Interface.AllDataInterFace
import com.test.mymadical.Interface.ClickInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var recycleview_data: RecyclerView
    lateinit var relative_no_data: RelativeLayout
    lateinit var img_search: ImageView
    var Pagenumber = 1
    var airlinesdata: ArrayList<Daum> = ArrayList()
    var Searchairlinesdata: ArrayList<Daum> = ArrayList()
    var mAdepterAirlines: AdepterAirlines? = null
    private var IsLAstLoading = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        fatchXml()


        val mLayoutManager = LinearLayoutManager(this)

        recycleview_data.layoutManager = mLayoutManager
        recycleview_data.setLayoutManager(mLayoutManager)
        mAdepterAirlines =
            AdepterAirlines(airlinesdata as List<Daum>, baseContext)
        recycleview_data.adapter = mAdepterAirlines
        recycleview_data.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (dy > 0) //check for scroll down
                {
                    val visibleItemCount: Int = mLayoutManager.getChildCount()
                    val totalItemCount: Int = mLayoutManager.getItemCount()
                    val pastVisiblesItems: Int = mLayoutManager.findFirstVisibleItemPosition()
                    if (IsLAstLoading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount
                            &&
                            recyclerView.getChildAt(recyclerView.childCount - 1).bottom <= recyclerView.height
                        ) {


                            if (Utils().isNetworkAvailable(this@MainActivity)) {

                                IsLAstLoading = false
                                Pagenumber = Pagenumber.plus(1)
                                getcategory()
                            } else {
                                Utils().showToastShortForNoInternet(this@MainActivity)
                            }
                        }
                    }
                }

            }
        })


        if (Utils().isNetworkAvailable(this)) {
            getcategory()
        } else {
            Utils().showToastShortForNoInternet(this)
        }


    }


    private fun fatchXml() {
        img_search = findViewById(R.id.img_search)
        recycleview_data = findViewById(R.id.recycleview_data)
        relative_no_data = findViewById(R.id.relative_no_data)

    }

    private fun getcategory() {
        val progress = LayoutInflater.from(this)
            .inflate(R.layout.custom_progress_dialog, null)


        val builder = AlertDialog.Builder(this)
            .setView(progress)
        val AlertDialog = builder.create()
        AlertDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        AlertDialog.setCancelable(false) // disable dismiss by tapping outside of the dialog

        AlertDialog.show()

        val creation: AllDataInterFace =
            Retroclient.getSingleTonClient()!!.create(AllDataInterFace::class.java)
        val call = creation.insertdata(Pagenumber.toString(), "5")


        call.enqueue(object : Callback<Root> {
            override fun onResponse(call: Call<Root>, response: Response<Root>) {

                if (response.isSuccessful) {

                    airlinesdata = response.body()?.data as ArrayList<Daum>
                    mAdepterAirlines?.notifyDataSetChanged()




                    try {
                        if (response.code() == 200) {
                            AlertDialog.dismiss()

                            if (response.body() == null) {
                                return
                            }
                            if (Pagenumber == 0) {
                                airlinesdata.clear()
                            }
                            if (response.body()?.data == null) {
                                if (Pagenumber != 0) {
                                    if (airlinesdata.size > 0) {
                                        mAdepterAirlines!!.notifyDataSetChanged()
                                        Log.e("ddd", "1   " + airlinesdata.size)

                                    }
                                } else {
                                    relative_no_data.visibility = View.VISIBLE

                                }
                                return
                            }
                            if (Pagenumber == 0) {
                                if (response.body()?.data?.size!! > 0) {
                                    relative_no_data.visibility = View.GONE
                                    airlinesdata.addAll(response.body()?.data as ArrayList<Daum>)
                                } else {
                                    if (Pagenumber == 0) {
                                        relative_no_data.visibility = View.VISIBLE
                                    } else {
                                        relative_no_data.visibility = View.GONE
                                    }
                                }
                                mAdepterAirlines!!.notifyDataSetChanged()
                            } else {
                                relative_no_data.visibility = View.GONE
                                if (airlinesdata.size > 0) {

                                    Log.e("ddd", "2  " + airlinesdata.size)
                                    mAdepterAirlines!!.notifyDataSetChanged()

                                }
                                if (response.body()?.data?.size!! > 0) {

                                    airlinesdata.addAll(response.body()?.data as ArrayList<Daum>)
                                }
                            }
                        } else {
                            if (Pagenumber != 0) {
                                if (airlinesdata.size > 0) {

                                    mAdepterAirlines!!.notifyDataSetChanged()
                                    Log.e("ddd", "3   " + airlinesdata.size)

                                }
                            } else {
                                relative_no_data.visibility = View.VISIBLE
                            }

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        if (Pagenumber != 0) {
                            if (airlinesdata.size > 0) {

                                mAdepterAirlines!!.notifyDataSetChanged()
                                Log.e("ddd", "12121   " + airlinesdata.size)

                            }
                        } else {
                            relative_no_data.visibility = View.VISIBLE
                        }
                    }

                }
                }

                override fun onFailure(call: Call<Root>, t: Throwable) {
                    Log.e("DSFSF", t.message + " hii")
                 }

            })


        }

    private fun searchdata(name: String) {
        for (item in airlinesdata){
            if (item.name.contains(name)||item.airline.get(0).country.contains(name)){
                Searchairlinesdata.add(item)
            }
        }

    }


}