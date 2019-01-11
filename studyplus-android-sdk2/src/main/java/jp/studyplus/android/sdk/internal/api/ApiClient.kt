package jp.studyplus.android.sdk.internal.api

import android.content.Context
import jp.studyplus.android.sdk.record.StudyRecord
import kotlinx.coroutines.Deferred

internal object ApiClient {
    fun postStudyRecords(context: Context, studyRecord: StudyRecord, consumerKey: String): Deferred<Long?> {
        return ApiService(ApiManager.client).post(CertificationStore.create(context).apiCertification(consumerKey), studyRecord.toJson())
    }
}
