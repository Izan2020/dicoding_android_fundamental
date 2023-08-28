package com.glantrox.dicoding_android_fundamental.core.di

import android.content.Context
import androidx.room.Room
import com.glantrox.dicoding_android_fundamental.core.data.local.FavouriteDatabase
import com.glantrox.dicoding_android_fundamental.core.data.remote.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun providesLocalDatabase(
        @ApplicationContext context: Context
    ): FavouriteDatabase {
        return Room.databaseBuilder(context, FavouriteDatabase::class.java, "favourite.db")
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun providesRemoteDataSource(): ApiInterface {
        val authInterceptor = Interceptor { chain ->
            val req  = chain.request()
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", "token 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")
                .build()
            chain.proceed(requestHeaders)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiInterface::class.java)
    }


}