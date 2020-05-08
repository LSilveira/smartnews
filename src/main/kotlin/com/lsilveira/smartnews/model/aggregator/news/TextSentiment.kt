package com.lsilveira.smartnews.model.aggregator.news

enum class TextSentiment(value: Long)
{
    VERY_POSITIVE(2),
    POSITIVE(1),
    NEUTRAL(0),
    NEGATIVE(-1),
    VERY_NEGATIVE(-2)
}