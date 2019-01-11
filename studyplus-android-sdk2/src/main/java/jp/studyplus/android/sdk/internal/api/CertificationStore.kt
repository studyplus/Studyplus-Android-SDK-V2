package jp.studyplus.android.sdk.internal.api

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import jp.studyplus.android.sdk.BuildConfig
import org.json.JSONObject

internal class CertificationStore
private constructor(private val preferences: SharedPreferences) {

    fun isAuthenticated(consumerKey: String) = tokenMap()[consumerKey]?.isNotEmpty() ?: false

    fun apiCertification(consumerKey: String): String {
        return tokenMap()[consumerKey] ?: throw IllegalStateException()
    }

    fun update(data: Intent, consumerKey: String) {
        val code = data.getStringExtra(EXTRA_SP_AUTH_RESULT_CODE)
        if (RESULT_CODE_AUTHENTICATED == code) {
            preferences.edit()?.apply {
                val newMap = tokenMap().plus(consumerKey to data.getStringExtra(EXTRA_SP_AUTH_ACCESS_TOKEN))
                putString(KEY_ACCESS_TOKEN_MAP, toJson(newMap))
                apply()
            }
        }
    }

    @SuppressLint("ApplySharedPref")
    fun migration(consumerKey: String) {
        val token = preferences.getString(KEY_ACCESS_TOKEN, null)
        if (token.isNullOrEmpty()) {
            return
        }
        preferences.edit().apply {
            val map = toJson(mapOf(consumerKey to token))
            putString(KEY_ACCESS_TOKEN_MAP, map)
            remove(KEY_ACCESS_TOKEN)
            commit()
        }
    }

    private fun tokenMap(): Map<String, String> = fromJson(preferences.getString(KEY_ACCESS_TOKEN_MAP, null))

    private fun toJson(map: Map<String, String>): String {
        val json = JSONObject()
        map.forEach { (key, value) -> json.put(key, value) }
        return json.toString()
    }

    private fun fromJson(obj: String): Map<String, String> {
        val json = JSONObject(obj)
        val map = mutableMapOf<String, String>()
        json.keys().forEach { map[it] = json[it] as String }
        return map
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_ACCESS_TOKEN_MAP = "access_token_map"

        private const val EXTRA_SP_AUTH_RESULT_CODE = "sp_auth_result_code"
        private const val EXTRA_SP_AUTH_ACCESS_TOKEN = "sp_auth_access_token"

        private const val RESULT_CODE_AUTHENTICATED = "AUTHENTICATED"

        fun create(context: Context): CertificationStore {
            val prefName = "Certification:" + Uri.parse(BuildConfig.API_ENDPOINT).host
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return CertificationStore(pref)
        }
    }
}
