package com.soujunior.domain.use_case.auth

import com.soujunior.domain.model.request.GuardianModel
import com.soujunior.domain.repository.AuthRepository
import com.soujunior.domain.use_case.base.BaseUseCase

abstract class GetGuardianDataUseCase (
    private val authRepository: AuthRepository
    ) : BaseUseCase<GuardianModel, String>() {

}