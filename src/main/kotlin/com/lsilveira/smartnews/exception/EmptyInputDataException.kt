package com.lsilveira.smartnews.exception

class EmptyInputDataException : Exception
{
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}