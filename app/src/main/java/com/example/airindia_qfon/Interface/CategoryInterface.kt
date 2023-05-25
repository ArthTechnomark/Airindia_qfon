package com.test.mymadical.Interface

import com.example.airindia_qfon.Modelclass.Root
import retrofit2.Call
import retrofit2.http.*

//asd
interface AllDataInterFace {
    @GET("qfonapp-passenger/")
    fun insertdata(
        @Query("page") page: String?,
        @Query("size") size: String?
    ): Call<Root>
}

