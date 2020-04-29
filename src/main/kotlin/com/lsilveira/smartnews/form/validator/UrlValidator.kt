package com.lsilveira.smartnews.form.validator

import com.lsilveira.smartnews.util.UrlHelper
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class UrlValidator : ConstraintValidator<UrlConstraint, String>
{
    override fun isValid(url: String?, context: ConstraintValidatorContext?): Boolean
    {
        return url != null && UrlHelper.validateUrl(url)
    }
}