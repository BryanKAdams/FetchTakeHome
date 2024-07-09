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

    /**
     * https://developer.android.com/training/dependency-injection/hilt-testing
     * @Module
     * @TestInstallIn(
     *     components = [SingletonComponent::class],
     *     replaces = [AnalyticsModule::class]
     * )
     * abstract class FakeAnalyticsModule {
     *
     *   @Singleton
     *   @Binds
     *   abstract fun bindAnalyticsService(
     *     fakeAnalyticsService: FakeAnalyticsService
     *   ): AnalyticsService
     * }
     */
    // this is needed because we are providing an implementation when requesting an interface
    // we use an interface to allow for testability
    @Singleton
    @Provides
    fun provideItemsRemoteDataSource(httpClient: HttpClient): IItemsRemoteDataSource =
        ItemsRemoteDataSource(httpClient)

    // same with this one
    @Singleton
    @Provides
    fun provideItemsRepository(
        itemsRemoteDataSource: IItemsRemoteDataSource
    ): IItemsRepository = ItemsRepository(itemsRemoteDataSource)
}