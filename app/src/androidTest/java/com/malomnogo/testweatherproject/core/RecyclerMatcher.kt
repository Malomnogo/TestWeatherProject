package com.malomnogo.testweatherproject.core

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class RecyclerViewMatcher(
    private val position: Int,
    private val targetViewId: Int = -1,
    private val recyclerViewId: Int
) : TypeSafeMatcher<View>() {

    private var resources: Resources? = null
    private var childView: View? = null

    override fun describeTo(description: Description) {
        var idDescription = recyclerViewId.toString()
        if (this.resources != null) {
            idDescription = try {
                this.resources!!.getResourceName(recyclerViewId)
            } catch (e: Resources.NotFoundException) {
                String.format("%s (resource name not found)", recyclerViewId)
            }
        }

        description.appendText("RecyclerView with id: $idDescription at position: $position")
    }

    override fun matchesSafely(view: View): Boolean {

        this.resources = view.resources

        if (childView == null) {
            val recyclerView = view.rootView.findViewById<RecyclerView>(recyclerViewId)
            if (recyclerView.id == recyclerViewId && recyclerView.adapter != null) {
                val adapter = recyclerView.adapter!!
                if (position >= 0 && position < adapter.itemCount) {
                    recyclerView.scrollToPosition(position)
                    recyclerView.measure(0, 0)
                    recyclerView.layout(0, 0, recyclerView.width, recyclerView.height)
                    
                    var viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                    if (viewHolder == null) {
                        recyclerView.smoothScrollToPosition(position)
                        var attempts = 0
                        while (viewHolder == null && attempts < 10) {
                            try {
                                Thread.sleep(50)
                            } catch (e: InterruptedException) {
                                Thread.currentThread().interrupt()
                                break
                            }
                            viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                            attempts++
                        }
                    }
                    if (viewHolder != null) {
                        childView = viewHolder.itemView
                    }
                }
            } else {
                return false
            }
        }

        return if (childView == null) {
            false
        } else if (targetViewId == -1) {
            view === childView
        } else {
            val targetView = childView!!.findViewById<View>(targetViewId)
            view === targetView
        }
    }
}