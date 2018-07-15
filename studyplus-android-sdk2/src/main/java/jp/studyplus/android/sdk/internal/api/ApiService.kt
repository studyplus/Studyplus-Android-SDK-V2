package jp.studyplus.android.sdk.internal.api

import io.reactivex.Observable
import jp.studyplus.android.sdk.internal.api.response.PostStudyRecordsResponse
import jp.studyplus.android.sdk.record.StudyRecord
import retrofit2.http.*

interface ApiService {
    @Headers(value = [
        "Accept: application/json",
        "Content-type: application/json"
    ])
    @POST("/v1/study_records")
    fun postStudyRecords(
            // TODO:https://studyplus.slack.com/archives/C0HD1DLM9/p1530504393000126
            // ｀HTTP_AUTHORIZATION｀ → ｀Authorization｀
            @Header("Authorization") oauth: String,
            @Body studyRecord: StudyRecord)
            : Observable<PostStudyRecordsResponse>
}