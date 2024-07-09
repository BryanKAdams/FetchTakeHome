package com.bryankeltonadams.fetchtakehometest.data.items

import com.bryankeltonadams.fetchtakehometest.data.model.Item
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
) : IItemsRemoteDataSource {
    override suspend fun getItems(): Result<List<Item>> {
        return try {
            Result.success(
                // base url is built into HttpClient
                // I could have put the whole thing as the baseUrl
                // but this would allow for flexibility of getting
                // data from the same api for different endpoints
                client.get("hiring.json").body()
            )
            // catching this individually is not necessary since I'm
            // returning the same Result object either way
            // but I'm doing it for clarity and to demonstrate
            // how one could possibly catch specific exceptions
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            Result.failure(e)
        } catch (e: ClientRequestException) {
            // 4xx - responses
            Result.failure(e)
        } catch (e: ServerResponseException) {
            // 5xx - responses
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
