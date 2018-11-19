package jp.studyplus.android.sdk.internal.api

import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

internal class MockApiService(private val client: OkHttpClient) {

    fun post(json: String): Deferred<Long?> {
        val server = MockWebServer()
        server.enqueue(MockResponse().setBody(""""{"record_id": 9999L}"""))
        server.start()

        val body = createPostBody(json)
        val request = Request.Builder()
                .url(server.url("/v1/study_records"))
                .post(body)
                .build()
        val call = client.newCall(request)

        return execute(call)
    }
}