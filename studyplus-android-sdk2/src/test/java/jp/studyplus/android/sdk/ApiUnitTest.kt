package jp.studyplus.android.sdk

import jp.studyplus.android.sdk.internal.api.MockApiClient
import jp.studyplus.android.sdk.record.StudyRecordBuilder
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals
import kotlin.test.assertNull

@RunWith(RobolectricTestRunner::class)
class ApiUnitTest {

    @Test
    fun mockApi() {
        val record = StudyRecordBuilder().build()
        runBlocking {
            try {
                val deferred = MockApiClient.postStudyRecords(null, record)
                val recordId = deferred.await()

                assertEquals(recordId, 9999L)
            } catch (t: Throwable) {
                assertNull(t)
            }
        }
    }
}