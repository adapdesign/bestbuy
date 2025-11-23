package dev.androi.bestbuy.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.androi.bestbuy.data.details.DetailsApi
import dev.androi.bestbuy.data.search.SearchApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val USER_AGENT_KEY = "User-Agent"

    // Server returns a network error unless certain user agents are provided, using my current browser as an example to get this to work:
    private const val USER_AGENT_OVERRIDE =
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:145.0) Gecko/20100101 Firefox/145.0"
    private const val BASE_URL = "https://www.bestbuy.ca/"

    private val moshi: Moshi by lazy {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    private val headerInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .header(USER_AGENT_KEY, USER_AGENT_OVERRIDE)
            .build()
        chain.proceed(request)
    }

    private val okHttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            // Needed to increase readTimeout or sometimes API call gets dropped
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(headerInterceptor)
            .build()
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    val searchApi: SearchApi by lazy {
        retrofit.create(SearchApi::class.java)
    }

    val productDetailsApi: DetailsApi by lazy {
        retrofit.create(DetailsApi::class.java)
    }
}