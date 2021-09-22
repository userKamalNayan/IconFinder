package kakcho.test.core.data.network

import com.google.gson.GsonBuilder
import kakcho.test.core.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Kamal Nayan on 22-09-2021 at 11:22
 */

inline fun <reified T> createWebService(url: String): T {
    val gson = GsonBuilder()
        .setLenient()
        .create()

    val client: OkHttpClient? = OkHttpClient.Builder().addInterceptor { chain ->
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer "+BuildConfig.API_KEY)
            //.addHeader("Accept", "application/json")
            .build()
        chain.proceed(newRequest)
    }.build()

    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    return retrofit.create(T::class.java)
}