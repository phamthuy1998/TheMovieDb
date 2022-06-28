package com.thuypham.ptithcm.baseapp.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout

class FlingBehavior : AppBarLayout.Behavior {
    private var isPositive = false

    constructor()
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun onNestedFling(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout, target: View, velocityX: Float,
        velocityY: Float, consumed: Boolean
    ): Boolean {
        var velocityY1 = velocityY
        var consumed1 = consumed
        if (velocityY1 > 0 && !isPositive || velocityY1 < 0 && isPositive) {
            velocityY1 *= -1
        }
        if (target is RecyclerView && velocityY1 < 0) {
            val firstChild = target.getChildAt(0)
            val childAdapterPosition = target.getChildAdapterPosition(firstChild)
            consumed1 = childAdapterPosition > TOP_CHILD_FLING_THRESHOLD
        }
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY1, consumed1)
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout, child: AppBarLayout,
        target: View, dx: Int, dy: Int, consumed: IntArray, type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        isPositive = dy > 0
    }

    companion object {
        private const val TOP_CHILD_FLING_THRESHOLD = 3
    }
}