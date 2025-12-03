package com.malomnogo.presentation.weather.views

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.R as MaterialR
import com.malomnogo.presentation.R
import com.malomnogo.presentation.core.views.CustomImageView
import com.malomnogo.presentation.weather.DayUiState

class DayForecastAdapter : RecyclerView.Adapter<DayForecastAdapter.DayViewHolder>() {

    private val items = mutableListOf<DayUiState>()

    fun update(list: List<DayUiState>) {
        val diffResult = DiffUtil.calculateDiff(
            DayDiffUtilCallback(oldList = items, newList = list)
        )
        items.clear()
        items.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DayViewHolder(parent.context)

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class DayViewHolder(context: Context) : RecyclerView.ViewHolder(
        createDayView(context)
    ) {
        private val dateTextView: TextView
        private val maxTemperatureTextView: TextView
        private val minTemperatureTextView: TextView
        private val iconImageView: CustomImageView

        init {
            val view = itemView as LinearLayout
            dateTextView = view.findViewById(R.id.dayDateTextView)
            maxTemperatureTextView = view.findViewById(R.id.dayMaxTemperatureTextView)
            minTemperatureTextView = view.findViewById(R.id.dayMinTemperatureTextView)
            iconImageView = view.findViewById(R.id.dayIconImageView)
        }

        fun bind(item: DayUiState) {
            item.update(object : ShowDay {
                override fun showDate(date: String) {
                    dateTextView.text = date
                }

                override fun showMaxTemperature(maxTemperature: String) {
                    maxTemperatureTextView.text = maxTemperature
                }

                override fun showMinTemperature(minTemperature: String) {
                    minTemperatureTextView.text = minTemperature
                }

                override fun showIcon(iconUrl: String) {
                    iconImageView.show(iconUrl)
                }

                override fun change(visible: Boolean) {
                    itemView.visibility = if (visible) android.view.View.VISIBLE else android.view.View.GONE
                }
            })
        }
    }

    private class DayDiffUtilCallback(
        private val oldList: List<DayUiState>,
        private val newList: List<DayUiState>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}

private fun createDayView(context: Context): LinearLayout {
    val layout = LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        val padding = context.resources.getDimensionPixelSize(R.dimen.big_padding)
        setPadding(padding, padding, padding, padding)
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    val dateTextView = AppCompatTextView(context).apply {
        id = R.id.dayDateTextView
        TextViewCompat.setTextAppearance(
            this,
            MaterialR.style.TextAppearance_MaterialComponents_Body1
        )
        setTextColor(ContextCompat.getColor(context, R.color.black))
        layoutParams = LinearLayout.LayoutParams(
            0,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            1f
        )
    }

    val iconImageView = CustomImageView(context).apply {
        id = R.id.dayIconImageView
        layoutParams = LinearLayout.LayoutParams(
            context.resources.getDimensionPixelSize(R.dimen.icon_size),
            context.resources.getDimensionPixelSize(R.dimen.icon_size)
        ).apply {
            val margin = context.resources.getDimensionPixelSize(R.dimen.small_padding)
            setMargins(margin, 0, margin, 0)
        }
    }

    val maxTemperatureTextView = AppCompatTextView(context).apply {
        id = R.id.dayMaxTemperatureTextView
        TextViewCompat.setTextAppearance(
            this,
            MaterialR.style.TextAppearance_MaterialComponents_Body1
        )
        setTextColor(ContextCompat.getColor(context, R.color.black))
        layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            val margin = context.resources.getDimensionPixelSize(R.dimen.small_padding)
            setMargins(margin, 0, margin, 0)
        }
    }

    val minTemperatureTextView = AppCompatTextView(context).apply {
        id = R.id.dayMinTemperatureTextView
        TextViewCompat.setTextAppearance(
            this,
            MaterialR.style.TextAppearance_MaterialComponents_Body1
        )
        setTextColor(ContextCompat.getColor(context, R.color.black))
        layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    layout.addView(dateTextView)
    layout.addView(iconImageView)
    layout.addView(maxTemperatureTextView)
    layout.addView(minTemperatureTextView)

    return layout
}

