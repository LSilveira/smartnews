package com.lsilveira.smartnews.form

import com.lsilveira.smartnews.form.validator.UrlConstraint
import com.lsilveira.smartnews.model.aggregator.AggregatorType
import javax.validation.constraints.NotEmpty

class AggregatorForm
{
    var aggregatorType: AggregatorType = AggregatorType.RSS // default aggregator

    @NotEmpty(message = "{validation.not_empty.name}")
    @UrlConstraint
    var url: String = ""

    @NotEmpty(message = "{validation.not_empty.name}")
    var topic: String = ""
}