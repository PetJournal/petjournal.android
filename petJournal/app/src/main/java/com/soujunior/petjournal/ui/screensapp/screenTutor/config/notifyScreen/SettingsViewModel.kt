package com.soujunior.petjournal.ui.screensapp.screenTutor.config.notifyScreen

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.use_case.preference.GetDarkModePreferenceUseCase
import com.soujunior.domain.use_case.preference.GetSystemThemePreferenceUseCase
import com.soujunior.domain.use_case.preference.SaveDarkModePreferenceUseCase
import com.soujunior.domain.use_case.preference.SaveSystemThemePreferenceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getDarkModePreferenceUseCase: GetDarkModePreferenceUseCase,
    private val saveDarkModePreferenceUseCase: SaveDarkModePreferenceUseCase,
    private val getSystemThemePreferenceUseCase: GetSystemThemePreferenceUseCase,
    private val saveSystemThemePreferenceUseCase: SaveSystemThemePreferenceUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        observeDarkModePreference()
        observeSystemThemePreference()
    }

    private fun observeDarkModePreference() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getDarkModePreferenceUseCase().collect { isDark ->
                _uiState.update {
                    it.copy(
                        isDarkMode = isDark,
                        isLoading = false,
                    )
                }
            }
        }
    }

    private fun observeSystemThemePreference() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getSystemThemePreferenceUseCase().collect { isSystem ->
                _uiState.update {
                    it.copy(
                        isSystemTheme = isSystem,
                        isLoading = false,
                    )
                }
            }
        }
    }

    fun updatePermissionsStatus(context: Context) {
        val isNotificationEnabled =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                true
            }

        val isExactAlarmEnabled =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.canScheduleExactAlarms()
            } else {
                true
            }

        _uiState.update {
            it.copy(
                isNotificationEnabled = isNotificationEnabled,
                isExactAlarmEnabled = isExactAlarmEnabled,
            )
        }
    }

    fun toggleDarkMode(isDark: Boolean) {
        viewModelScope.launch {
            saveDarkModePreferenceUseCase(isDark)
        }
    }

    fun toggleSystemTheme(isSystem: Boolean) {
        viewModelScope.launch {
            saveSystemThemePreferenceUseCase(isSystem)
        }
    }
}
