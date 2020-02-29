package com.shunsukeshoji.giticons

import android.content.Context
import com.shunsukeshoji.giticons.api.GithubService
import com.shunsukeshoji.giticons.data.GithubRepository
import com.shunsukeshoji.giticons.db.GithubLocalCache
import com.shunsukeshoji.giticons.db.UserDatabase
import com.shunsukeshoji.giticons.presentation.home.HomeScreenViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.Executors

object injection {
    private fun provideRetrofit(context: Context): Retrofit {
        val httpClient = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", provideAuthToken(context))
                .build()

            chain.proceed(newRequest)
        }.build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://api.github.com/")
            .build()
    }

    private fun provideAuthToken(context: Context): String =
        context.resources.getString(R.string.access_token)


    private fun provideGithubService(context: Context): GithubService {
        val retrofit = provideRetrofit(context)
        return retrofit.create(GithubService::class.java)
    }

    private fun provideCache(context: Context): GithubLocalCache {
        val database = UserDatabase.getInstance(context)
        return GithubLocalCache(database.usersDao(), Executors.newSingleThreadExecutor())
    }

    private fun provideGithubRepository(context: Context): GithubRepository {
        return GithubRepository(
            provideGithubService(context),
            provideCache(context)
        )
    }

    fun provideViewModelFactory(context: Context): HomeScreenViewModel.ViewModelFactory {
        return HomeScreenViewModel.ViewModelFactory(provideGithubRepository(context))
    }
}