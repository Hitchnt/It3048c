package edu.ucandroid.weathernotice.ui.main

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientInstance {
    private var retrofit: Retrofit? = null;

    private val BASE_URL = "https://api.weatherbit.io"
    private val BASELOCATION_URL = "https://raw.githubusercontent.com"
    /**
     * the ? at the end of retrofit just means it can be null
     */
    val retrofitInstance : Retrofit?
        get() {
            // has this object been created yet?
            if (retrofit == null) {
                // create it!
                retrofit = retrofit2.Retrofit.Builder()
                        .baseUrl(BASELOCATION_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit
        }

}
