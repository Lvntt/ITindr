package dev.lantt.itindr.profile.domain.usecase

class ValidateNameUseCase {

    operator fun invoke(name: String) = name.isNotBlank()

}