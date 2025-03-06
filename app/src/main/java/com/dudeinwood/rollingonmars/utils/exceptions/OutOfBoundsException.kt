package com.dudeinwood.rollingonmars.utils.exceptions

import com.dudeinwood.rollingonmars.domain.model.Rover
import java.lang.Exception

class OutOfBoundsException(message: String, val rover: Rover? = null) : Exception(message)