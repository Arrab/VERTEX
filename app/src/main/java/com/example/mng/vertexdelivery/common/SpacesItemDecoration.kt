package com.example.mng.vertexdelivery.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecoration (internal  var spaces:Int): RecyclerView.ItemDecoration(){

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.right = spaces
        outRect.left = spaces
        outRect.bottom = spaces
        outRect.top = spaces
    }
}