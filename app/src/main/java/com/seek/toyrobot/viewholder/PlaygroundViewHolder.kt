package com.seek.toyrobot.viewholder

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.seek.toyrobot.model.Robot

class PlaygroundViewHolder(itemView: ImageView) : RecyclerView.ViewHolder(itemView) {
    fun bindView(robot: Robot) {
        itemView.background = robot.robotSrc;
        if (robot.isOccupied) {
            itemView.visibility = View.VISIBLE
            rotateImageInDirection(robot)
        } else {
            itemView.visibility = View.INVISIBLE
        }
    }

    /**
     * Rotate image in the robot direction
     */
    private fun rotateImageInDirection(robot: Robot) {
        var rotation: Float = when (robot.robotDirection) {
            Robot.Direction.NORTH -> 0f
            Robot.Direction.WEST -> 270f
            Robot.Direction.EAST -> 90f
            Robot.Direction.SOUTH -> 180f
        }
        itemView.rotation = rotation
    }
}