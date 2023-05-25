package com.example.airindia_qfon.Modelclass

import com.google.gson.annotations.SerializedName
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "AirIndia")

data class Root(
    val data: List<Daum>,
    val pagination: Pagination,
)

data class Daum(
    @SerializedName("_id")
    @PrimaryKey(autoGenerate = false)  val id: String,
    val name: String,
    val trips: Long,
    val airline: List<Airline>,
    @SerializedName("__v")
    val v: Long,
)

data class Airline(
    val id: Long,
    val name: String,
    val country: String,
    val logo: String,
    val slogan: String,
    @SerializedName("head_quaters")
    val headQuaters: String,
    val website: String,
    val established: String,
)

data class Pagination(
    val currentPage: Long,
    val currentItems: Long,
    val totalPages: Long,
    val totalItems: Long,
    val links: List<Link>,
)

data class Link(
    val rel: String,
    val page: Long,
    val href: String?,
    @SerializedName("a href")
    val aHref: String?,
)
