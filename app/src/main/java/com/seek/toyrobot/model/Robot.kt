package com.seek.toyrobot.model

import android.graphics.drawable.BitmapDrawable

/**
 * @author Kruti.Patel
 */
class Robot {
    enum class Direction {
        NORTH, SOUTH, WEST, EAST
    }
    lateinit var robotSrc: BitmapDrawable
    lateinit var robotDirection: Direction
    var isOccupied: Boolean = false

    /**
     * Overriding super method to customise robot object printable value
     */
    override fun toString(): String {
        return "Robot Direction : $robotDirection , Robot Occupied : $isOccupied"
    }
}
