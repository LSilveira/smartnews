package com.lsilveira.smartnews.form.validator

import javax.validation.Constraint
import kotlin.reflect.KClass


@MustBeDocumented
@Constraint(validatedBy = [UrlValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class UrlConstraint
(
        val message: String = "Invalid url",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Any>> = []
//        val payload: Array<KClass<out Payload?>> = []
)