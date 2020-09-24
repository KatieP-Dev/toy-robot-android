package com.seek.toyrobot

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seek.toyrobot.adapter.PlaygroundGridRecyclerAdapter
import com.seek.toyrobot.model.Robot
import com.seek.toyrobot.views.GridItemDecoration

/**
 * @author Kruti.Patel
 */
class MainActivity : AppCompatActivity() {

    //Default values
    private val gridAdapter = PlaygroundGridRecyclerAdapter()
    private val gridSize = 5 //Grid will be 5X5
    private val defaultPosition =  20
    private val totalGridSize = gridSize * gridSize

    //Variable values
    private lateinit var robotPlayground: RecyclerView
    private var currentPosition: Int = defaultPosition
    private lateinit var currentDirection: Robot.Direction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onResume() {
        super.onResume()
        initialiseView()
    }

    /**
     * Initialise views with loading the grid view and robot in it.
     */
    private fun initialiseView() {
        robotPlayground = findViewById<View>(R.id.robotPlayground) as RecyclerView
        robotPlayground.layoutManager = GridLayoutManager(this, gridSize)
        robotPlayground.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.HORIZONTAL
            )
        )
        robotPlayground.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        robotPlayground.addItemDecoration(GridItemDecoration(10, 2))

        robotPlayground.adapter = gridAdapter
        positionRobot(currentPosition)

        var btnRotateLeft = findViewById<View>(R.id.btnRotateLeft) as Button
        btnRotateLeft.setOnClickListener {
            rotateLeft()
        }

        var btnRotateRight = findViewById<View>(R.id.btnRotateRight) as Button
        btnRotateRight.setOnClickListener {
            rotateRight()
        }

        var btnMoveForward = findViewById<View>(R.id.btnMoveForward) as Button
        btnMoveForward.setOnClickListener {
            moveForward()
        }

        var btnResetPosition = findViewById<View>(R.id.btnResetPosition) as Button
        btnResetPosition.setOnClickListener {
            resetPosition()
        }

        var btnReportPosition = findViewById<View>(R.id.btnReportPosition) as Button
        btnReportPosition.setOnClickListener {
            reportPosition(false)
        }
    }

    /**
     * Place robot in the supplied position and direction
     */
    private fun positionRobot(position: Int) {

        if (!this::currentDirection.isInitialized) {
            this.currentDirection = Robot.Direction.NORTH
        }

        val gridItems = mutableListOf<Robot>()

        val icRobot = resources.getDrawable(R.drawable.ic_robot, null) as BitmapDrawable

        for (i in 0 until totalGridSize) {
            val robot = Robot()
            robot.isOccupied = false
            robot.robotDirection = currentDirection
            robot.robotSrc = icRobot
            if (i == position) {
                robot.isOccupied = true
            }
            gridItems.add(robot)
        }

        gridAdapter.setupGrid(gridItems)
    }

    /**
     * Rotate robot in 90 degrees in anticlockwise direction
     */
    private fun rotateLeft() {
        currentDirection = when (currentDirection) {
            Robot.Direction.NORTH -> Robot.Direction.WEST
            Robot.Direction.WEST -> Robot.Direction.SOUTH
            Robot.Direction.EAST -> Robot.Direction.NORTH
            Robot.Direction.SOUTH -> Robot.Direction.EAST
        }
        Log.e("rotateLeft", "" + currentDirection)
        positionRobot(currentPosition);
        robotPlayground.adapter?.notifyItemChanged(currentPosition)
        reportPosition(false)
    }

    /**
     * Rotate robot in 90 degrees in clockwise direction
     */
    private fun rotateRight() {
        currentDirection = when (currentDirection) {
            Robot.Direction.NORTH -> Robot.Direction.EAST
            Robot.Direction.WEST -> Robot.Direction.NORTH
            Robot.Direction.EAST -> Robot.Direction.SOUTH
            Robot.Direction.SOUTH -> Robot.Direction.WEST
        }
        positionRobot(currentPosition);
        robotPlayground.adapter?.notifyItemChanged(currentPosition)
        reportPosition(false)
    }

    /**
     * Move robot forward in the direction robot is in.
     */
    private fun moveForward() {
        when (currentDirection) {
            Robot.Direction.NORTH -> {
                if (currentPosition <= gridSize-1) {
                    Toast.makeText(this, "Sorry Robot cannot move any more.", Toast.LENGTH_LONG)
                        .show()
                } else {
                    currentPosition -= gridSize
                }
            }
            Robot.Direction.WEST -> {

                if (currentPosition <= 0) {
                    currentPosition = totalGridSize -1
                } else {
                    currentPosition -= 1
                }
            }
            Robot.Direction.EAST -> {
                if (currentPosition >= totalGridSize-1) {
                    currentPosition = 0
                } else {
                    currentPosition += 1
                }
            }
            Robot.Direction.SOUTH -> {
                if (currentPosition >= totalGridSize  - gridSize) {
                    Toast.makeText(this, "Sorry Robot cannot move anymore.", Toast.LENGTH_LONG)
                        .show()
                } else {
                    currentPosition += gridSize
                }
            }
        }

        positionRobot(currentPosition);
        robotPlayground.adapter?.notifyItemChanged(currentPosition)
        reportPosition(false)
    }

    /**
     * Reset the position of the robot to its original position. in this case it is
     */
    private fun resetPosition() {
        currentPosition = defaultPosition
        currentDirection = Robot.Direction.NORTH
        positionRobot(currentPosition);
        reportPosition(true)
        robotPlayground.adapter?.notifyItemChanged(currentPosition)
    }

    /**
     * Print robots current position in the grid in 'PLACE row,column,direction'
     */
    private fun reportPosition(isReset: Boolean) {
        var txtReportPosition = findViewById<View>(R.id.txtReportPosition) as TextView
        txtReportPosition.text = if (isReset) {
            ""
        } else {
            "PLACE ${currentPosition / 5},${currentPosition % 5},${currentDirection.name}"

        }
    }
}