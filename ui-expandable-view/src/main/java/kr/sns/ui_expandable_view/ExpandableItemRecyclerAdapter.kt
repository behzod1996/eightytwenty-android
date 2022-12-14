package kr.sns.ui_expandable_view

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class ExpandableItemRecyclerAdapter(
    private var adapter: ExpandableItemAdapter,
    private val itemClickCallback: (Int) -> Unit,
    private val selectedIndexPredicate: (Int) -> Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var showDividers: Boolean? = null
    @ColorInt
    var dividerColor: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val linearLayout = LinearLayout(parent.context)
        val contentView = adapter.inflateItemView(parent)
        val dividerView = parent.inflate(R.layout.divider_item)
        showDividers?.let { dividerView.isVisible = it }
        dividerColor?.let { dividerView.setBackgroundColor(it) }

        linearLayout.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.addView(dividerView)
        linearLayout.addView(contentView)
        return ViewHolder(linearLayout)
    }

    override fun getItemCount(): Int = adapter.getItemsCount()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewGroup = holder.itemView as ViewGroup
        val itemView = itemViewGroup.getChildAt(1)
        val isItemSelected = selectedIndexPredicate(position)

        adapter.bindItemView(itemView, position, isItemSelected)

        holder.itemView.setOnClickListener { itemClickCallback.invoke(position) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}