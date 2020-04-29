package com.lsilveira.smartnews.exception

open class SystemException : Exception
{
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}