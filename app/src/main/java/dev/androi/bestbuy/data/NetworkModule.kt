package dev.androi.bestbuy.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.androi.bestbuy.data.details.DetailsApi
import dev.androi.bestbuy.data.details.ProductDetailsRepository
import dev.androi.bestbuy.data.details.ProductDetailsRepositoryImpl
import dev.androi.bestbuy.data.search.SearchApi
import dev.androi.bestbuy.data.search.SearchRepository
import dev.androi.bestbuy.data.search.SearchRepositoryImpl
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = RetrofitClient.retrofit

    @Provides @Singleton
    fun provideProductDetailsApi(retrofit: Retrofit): DetailsApi =
        retrofit.create(DetailsApi::class.java)

    @Provides @Singleton
    fun provideProductDetailsRepository(api: DetailsApi): ProductDetailsRepository =
        ProductDetailsRepositoryImpl(api)

    @Provides @Singleton
    fun provideSearchApi(retrofit: Retrofit): SearchApi =
        retrofit.create(SearchApi::class.java)

    @Provides @Singleton
    fun provideSearchRepository(api: SearchApi): SearchRepository =
        SearchRepositoryImpl(api)
}
