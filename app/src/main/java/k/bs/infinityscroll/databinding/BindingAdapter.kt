package k.bs.infinityscroll.databinding

import android.graphics.drawable.Drawable
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

class BindingAdapter {
    companion object {
        @JvmStatic
        @BindingAdapter("bind:verticalDivider")
        fun recyclerViewVerticalDivider(view: RecyclerView, verticalDivider: Drawable) {
            if (view.itemDecorationCount == 0 || view.getItemDecorationAt(view.itemDecorationCount - 1) !is DividerItemDecoration) {
                val decoration = DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL)
                decoration.setDrawable(verticalDivider)
                view.addItemDecoration(decoration)
            }
        }
    }
}