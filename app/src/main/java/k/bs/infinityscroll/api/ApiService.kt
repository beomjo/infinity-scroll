package k.bs.infinityscroll.api

import k.bs.infinityscroll.api.service.ApiJsonPlaceHolder

object ApiService {
    private const val baseUrl = "https://jsonplaceholder.typicode.com"

    fun jsonPlaceHolder() = RetrofitAdapter.getInstance(baseUrl)
        .create(ApiJsonPlaceHolder::class.java)
}