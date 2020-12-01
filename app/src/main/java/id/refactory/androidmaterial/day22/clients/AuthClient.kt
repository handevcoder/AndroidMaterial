package id.refactory.androidmaterial.day22.clients

import android.util.Log
import com.google.gson.GsonBuilder
import id.refactory.androidmaterial.BuildConfig
import id.refactory.androidmaterial.day22.services.AuthService
import id.refactory.androidmaterial.day22.utils.ConstantUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AuthClient {
    companion object {
        val service: AuthService by lazy {
            val httpLoggingInterceptor = HttpLoggingInterceptor { message ->
                if (BuildConfig.DEBUG) Log.e("LOG_API", message)
            }
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient()
                .newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .build()

            val retrofit = Retrofit
                .Builder()
                .baseUrl(ConstantUtil.BASE_URL_API)
                .client(okHttpClient)
                .addConverterFactory(
                    GsonConverterFactory.create(GsonBuilder().setLenient().create())
                )
                .build()
            retrofit.create(AuthService::class.java)
        }
    }
}