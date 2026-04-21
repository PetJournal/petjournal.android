package com.soujunior.petjournal.ui.screensapp.screenTutor.config.notifyScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.use_case.preference.GetDarkModePreferenceUseCase
import com.soujunior.domain.use_case.preference.SaveDarkModePreferenceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getDarkModePreferenceUseCase: GetDarkModePreferenceUseCase,
    private val saveDarkModePreferenceUseCase: SaveDarkModePreferenceUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        observeDarkModePreference()
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

    fun toggleDarkMode(isDark: Boolean) {
        viewModelScope.launch {
            saveDarkModePreferenceUseCase(isDark)
        }
    }
}
