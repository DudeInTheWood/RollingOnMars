package com.dudeinwood.rollingonmars.utils.enum

enum class Direction(val value: Char) {
    N('N'),
    E('E'),
    S('S'),
    W('W');

    companion object {
        fun directionFromChar(c: Char): Direction {
            return entries.find { it.value == c } ?: N
        }
    }
}