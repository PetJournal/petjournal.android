package com.soujunior.petjournal.ui.awaitingCodeScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.entities.auth.AwaitingCodeModel
import com.soujunior.domain.usecase.auth.AwaitingCodeUseCase
import kotlinx.coroutines.launch

class AwaitingCodeScreenViewModelImpl(
    private val awaitingCodeUseCase: AwaitingCodeUseCase
) : AwaitingCodeScreenViewModel() {

    override val success = MutableLiveData<String>()
    override val error = MutableLiveData<String>()

    override fun postForm(form: AwaitingCodeModel) {
        viewModelScope.launch {
            val result = awaitingCodeUseCase.execute(form)
            result.handleResult(::success, ::failed)
        }
    }

    override fun failed(exception: Throwable?) {
        if (exception is Error) {
            this.error.value = exception.message

        } else {
            this.error.value = "Erro desconhecido!"
        }
    }

    override fun success(exception: Throwable?) {

    }

}