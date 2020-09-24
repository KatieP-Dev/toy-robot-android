package com.seek.toyrobot.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.seek.toyrobot.R
import com.seek.toyrobot.model.Robot
import com.seek.toyrobot.viewholder.PlaygroundViewHolder

/**
 * @author Kruti.Patel
 */
class PlaygroundGridRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var gridList = listOf<Robot>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlaygroundViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_playground_grid, parent, false) as ImageView
        )
    }

    override fun getItemCount(): Int = gridList.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val robotViewHolder = viewHolder as PlaygroundViewHolder
        robotViewHolder.bindView(gridList[position])
    }

    /**
     * This method setups gridItems in the recycler view. This method must be called to load items in grid.
     * @param gridItemList list of @Robot to load in the grid
     */
    fun setupGrid(gridItemList: List<Robot>) {
        this.gridList = gridItemList
        notifyDataSetChanged()
    }
}