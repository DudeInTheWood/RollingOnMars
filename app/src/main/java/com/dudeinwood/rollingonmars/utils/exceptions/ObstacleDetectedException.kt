package com.dudeinwood.rollingonmars.utils.exceptions

import com.dudeinwood.rollingonmars.data.model.Rover
import java.lang.Exception

class ObstacleDetectedException(message: String, val rover: Rover? = null) : Exception(message)