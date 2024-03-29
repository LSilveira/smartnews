package com.lsilveira.smartnews.exception

class NewsException : SystemException
{
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}