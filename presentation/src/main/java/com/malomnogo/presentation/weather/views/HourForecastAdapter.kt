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
import com.malomnogo.presentation.weather.HourUiState

class HourForecastAdapter : RecyclerView.Adapter<HourForecastAdapter.HourViewHolder>() {

    private val items = mutableListOf<HourUiState>()

    fun update(list: List<HourUiState>) {
        val diffResult = DiffUtil.calculateDiff(
            HourDiffUtilCallback(oldList = items, newList = list)
        )
        items.clear()
        items.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HourViewHolder(parent.context)

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class HourViewHolder(context: Context) : RecyclerView.ViewHolder(
        createHourView(context)
    ) {
        private val timeTextView: TextView
        private val temperatureTextView: TextView

        init {
            val view = itemView as LinearLayout
            timeTextView = view.findViewById(R.id.hourTimeTextView)
            temperatureTextView = view.findViewById(R.id.hourTemperatureTextView)
        }

        fun bind(item: HourUiState) {
            item.update(object : ShowHour {
                override fun showTime(time: String) {
                    timeTextView.text = time
                }

                override fun showTemperature(temperature: String) {
                    temperatureTextView.text = temperature
                }

                override fun change(visible: Boolean) {
                    itemView.visibility = if (visible) android.view.View.VISIBLE else android.view.View.GONE
                }
            })
        }
    }

    private class HourDiffUtilCallback(
        private val oldList: List<HourUiState>,
        private val newList: List<HourUiState>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}

private fun createHourView(context: Context): LinearLayout {
    val layout = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER
        val padding = context.resources.getDimensionPixelSize(R.dimen.small_padding)
        setPadding(padding, padding, padding, padding)
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    val timeTextView = AppCompatTextView(context).apply {
        id = R.id.hourTimeTextView
        TextViewCompat.setTextAppearance(
            this,
            MaterialR.style.TextAppearance_MaterialComponents_Body2
        )
        setTextColor(ContextCompat.getColor(context, R.color.black))
        gravity = Gravity.CENTER
    }

    val temperatureTextView = AppCompatTextView(context).apply {
        id = R.id.hourTemperatureTextView
        TextViewCompat.setTextAppearance(
            this,
            MaterialR.style.TextAppearance_MaterialComponents_Body1
        )
        setTextColor(ContextCompat.getColor(context, R.color.black))
        gravity = Gravity.CENTER
    }

    layout.addView(timeTextView)
    layout.addView(temperatureTextView)

    return layout
}

