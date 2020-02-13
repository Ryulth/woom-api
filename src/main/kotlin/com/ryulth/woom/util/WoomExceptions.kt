package com.ryulth.woom.util

import java.lang.RuntimeException

open class WoomRuntimeExceptions(message: String) : RuntimeException(message)
class AccountAlreadyExistException(message: String) : WoomRuntimeExceptions(message)
