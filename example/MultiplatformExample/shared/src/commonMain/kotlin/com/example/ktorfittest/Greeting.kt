package com.example.ktorfittest

import de.jensklingenberg.ktorfit.converter.builtin.CallConverterFactory
import de.jensklingenberg.ktorfit.converter.builtin.FlowConverterFactory
import de.jensklingenberg.ktorfit.ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

val ktorfit = ktorfit {
    baseUrl(StarWarsApi.baseUrl)
    httpClient(HttpClient {
        install(ContentNegotiation) {
            json(Json { isLenient = true; ignoreUnknownKeys = true })
        }
    })
    converterFactories(
        FlowConverterFactory(),
        CallConverterFactory()
    )
}


val starWarsApi = ktorfit.create<StarWarsApi>()

class Greeting {
    fun greeting(): String {

        loadData()
        return "Hello, ${Platform().platform}! Look in the LogCat"
    }


}

fun loadData() {
    GlobalScope.launch {
        val response = starWarsApi.getPersonByIdResponse(3)
        println("Ktorfit:" + Platform().platform + ":" + response)
    }
}