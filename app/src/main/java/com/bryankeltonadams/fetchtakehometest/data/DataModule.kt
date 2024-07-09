package com.bryankeltonadams.fetchtakehometest.data

import com.bryankeltonadams.fetchtakehometest.data.items.IItemsRemoteDataSource
import com.bryankeltonadams.fetchtakehometest.data.items.IItemsRepository
import com.bryankeltonadams.fetchtakehometest.data.items.ItemsRemoteDataSource
import com.bryankeltonadams.fetchtakehometest.data.items.ItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideItemsRemoteDataSource(httpClient: HttpClient): IItemsRemoteDataSource =
        ItemsRemoteDataSource(httpClient)

    @Singleton
    @Provides
    fun provideItemsRepository(
        itemsRemoteDataSource: IItemsRemoteDataSource
    ): IItemsRepository = ItemsRepository(itemsRemoteDataSource)
}