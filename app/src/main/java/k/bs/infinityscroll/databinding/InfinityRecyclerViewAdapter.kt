package k.bs.infinityscroll.databinding

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import k.bs.infinityscroll.BR
import k.bs.infinityscroll.R

const val VIEW_TYPE_NORMAL = 0
const val VIEW_TYPE_LOADING = 1

class InfinityRecyclerViewAdapter<ITEM : Any, B : ViewDataBinding>(
    layout: Int,
    bindingVariableId: Int? = null,
    val load: () -> Unit
) : BaseRecyclerViewAdapter<ITEM, B>(layout, bindingVariableId) {

    override fun getItemViewType(position: Int): Int {
        return if ((position == itemCount - 1)) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<B> {
        return if (viewType == VIEW_TYPE_NORMAL)
            BaseViewHolder(
                layout = layout,
                parent = parent,
                bindingVariableId = bindingVariableId
            )
        else BaseViewHolder(
            layout = R.layout.item_loading,
            parent = parent,
            bindingVariableId = BR.vm
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<B>, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_NORMAL)
            (holder).onBindViewHolder(items[position])
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val pos = layoutManager.findLastCompletelyVisibleItemPosition()
                val numItems = recyclerView.adapter?.itemCount

                val offset = 1

                if (pos >= numItems!! - offset) {
                    load.invoke()
                }
            }
        })
    }
}