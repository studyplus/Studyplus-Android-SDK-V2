package jp.studyplus.android.sdk.internal.api

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import jp.studyplus.android.sdk.BuildConfig

internal class CertificationStore
private constructor(private val preferences: SharedPreferences) {

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"

        private const val EXTRA_SP_AUTH_RESULT_CODE = "sp_auth_result_code"
        private const val EXTRA_SP_AUTH_ACCESS_TOKEN = "sp_auth_access_token"

        private const val RESULT_CODE_AUTHENTICATED = "AUTHENTICATED"

        fun create(context: Context): CertificationStore {
            val prefName = "Certification:" + Uri.parse(BuildConfig.API_ENDPOINT).host
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return CertificationStore(pref)
        }
    }

    fun isAuthenticated(): Boolean {
        val token = preferences.getString(KEY_ACCESS_TOKEN, "")
        return !token.isNullOrEmpty()
    }

    fun getOAuthAccessToken(): String {
        val certification = preferences.getString(KEY_ACCESS_TOKEN, "")
        return "OAuth $certification"
    }

    fun update(data: Intent) {
        val code = data.getStringExtra(EXTRA_SP_AUTH_RESULT_CODE).orEmpty()
        if (RESULT_CODE_AUTHENTICATED == code) {
            preferences.edit()?.apply {
                putString(KEY_ACCESS_TOKEN, data.getStringExtra(EXTRA_SP_AUTH_ACCESS_TOKEN))
                apply()
            }
        }
    }
}
