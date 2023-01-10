package com.example.applicationforcustomerinformation.network
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL =
    "https://lookup.binlist.net/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface BankApiService {
    @GET("{number}")
    suspend fun getUser(@Path("number") number: String?): List<clientData>
}

object BankApi {
    val retrofitService : BankApiService by lazy {
        retrofit.create(BankApiService::class.java) }
}