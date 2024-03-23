package com.dev.githubuser.ui.main

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object PreferencesManager {
    private const val PREF_NAME = "ThemePrefs"
    private const val KEY_THEME = "theme"

    private lateinit var preferences: SharedPreferences

    private val _themeFlow: MutableStateFlow<Theme> = MutableStateFlow(Theme.LIGHT)
    val themeFlow: StateFlow<Theme> = _themeFlow

    fun initialize(context: Context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        _themeFlow.value = getSavedTheme()
    }

    private fun getSavedTheme(): Theme {
        val savedThemeOrdinal = preferences.getInt(KEY_THEME, Theme.LIGHT.ordinal)
        return Theme.values().getOrElse(savedThemeOrdinal) { Theme.LIGHT }
    }

    suspend fun saveTheme(theme: Theme) {
        preferences.edit {
            putInt(KEY_THEME, theme.ordinal)
        }
        _themeFlow.value = theme
    }
    fun getCurrentTheme(): Theme {
        return themeFlow.value
    }

}
