package com.bryankeltonadams.fetchtakehometest.data.items

import com.bryankeltonadams.fetchtakehometest.data.model.Item
import dagger.Provides
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface IItemsRemoteDataSource {
    suspend fun getItems(): Result<List<Item>>
}

@Singleton
class ItemsRemoteDataSource @Inject constructor(
    private val client: HttpClient
): IItemsRemoteDataSource {
    override suspend fun getItems(): Result<List<Item>> {
        return try {
            Result.success(
                client.get(urlString = "https://fetch-hiring.s3.amazonaws.com/hiring.json").body()
            )
        } catch (e: RedirectResponseException) {
            Result.failure(e)
        } catch (e: ClientRequestException) {
            Result.failure(e)
        } catch (e: ServerResponseException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
