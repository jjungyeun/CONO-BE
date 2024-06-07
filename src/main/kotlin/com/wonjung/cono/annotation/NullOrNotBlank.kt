package com.wonjung.cono.annotation

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [NullOrNotBlankValidator::class])
annotation class NullOrNotBlank(
    val message: String = "can be null but not blank",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class NullOrNotBlankValidator: ConstraintValidator<NullOrNotBlank, String> {
    override fun isValid(p0: String?, p1: ConstraintValidatorContext?): Boolean {
        return p0 == null || p0.isNotBlank()
    }

}
