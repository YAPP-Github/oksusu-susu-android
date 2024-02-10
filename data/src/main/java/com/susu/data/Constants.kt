package com.susu.data

import androidx.datastore.preferences.core.booleanPreferencesKey

object Constants {
    const val RETROFIT_TAG = "Retrofit"

    private const val SHOW_ONBOARD_VOTE = "Onboarding"
    val showOnboardingVoteKey = booleanPreferencesKey(SHOW_ONBOARD_VOTE)
}
