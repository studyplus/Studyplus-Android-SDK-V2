package jp.studyplus.android.sdk.record

import jp.studyplus.android.sdk.record.StudyRecord.Companion.getTime
import org.json.JSONObject
import java.security.InvalidParameterException
import java.text.SimpleDateFormat
import java.util.*

class StudyRecord {
    var recordedTime: String = getTime(GregorianCalendar(DATE_TIME_ZONE, DATE_LOCALE))

    var duration: Int = 0
    var comment: String = ""
    var amountTotal: Int? = null
    var startPosition: Int? = null
    var endPosition: Int? = null

    fun toJson() = JSONObject().apply {
        put("recorded_at", recordedTime)
        put("duration", duration)
        put("comment", comment)
        put("amountTotal", amountTotal)
        put("start_position", startPosition)
        put("end_position", endPosition)
    }.toString()

    companion object {
        private const val DATE_FORMAT = "yyyy'-'MM'-'dd' 'HH':'mm':'ss"
        private val DATE_LOCALE: Locale = Locale.US
        private val DATE_TIME_ZONE: TimeZone = TimeZone.getTimeZone("UTC")

        internal fun getTime(calendar: Calendar): String {
            val format = SimpleDateFormat(DATE_FORMAT, DATE_LOCALE)
            format.timeZone = calendar.timeZone
            return format.format(calendar.time)
        }
    }
}

class StudyRecordBuilder {

    private val studyRecord = StudyRecord()

    fun build(): StudyRecord {
        return studyRecord
    }

    fun setRecordedTime(calendar: Calendar): StudyRecordBuilder {
        studyRecord.recordedTime = getTime(calendar)
        return this
    }

    fun setDurationSeconds(duration: Int): StudyRecordBuilder {
        studyRecord.duration = duration
        return this
    }

    fun setComment(comment: String): StudyRecordBuilder {
        studyRecord.comment = comment
        return this
    }

    fun setAmountTotal(amount: Int): StudyRecordBuilder {
        if (studyRecord.startPosition != null
                || studyRecord.endPosition != null) {
            throw InvalidParameterException()
        }
        studyRecord.amountTotal = amount
        return this
    }

    fun setAmountRange(start: Int, end: Int): StudyRecordBuilder {
        if (studyRecord.amountTotal != null) {
            throw InvalidParameterException()
        }
        studyRecord.startPosition = start
        studyRecord.endPosition = end
        return this
    }
}
