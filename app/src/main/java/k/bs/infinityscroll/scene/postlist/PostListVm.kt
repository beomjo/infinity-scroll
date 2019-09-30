package k.bs.infinityscroll.scene.postlist

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import k.bs.infinityscroll.BR
import k.bs.infinityscroll.R
import k.bs.infinityscroll.api.ApiService
import k.bs.infinityscroll.base.BaseViewModel
import k.bs.infinityscroll.databinding.InfinityRecyclerViewAdapter
import k.bs.infinityscroll.databinding.ItemPostBinding
import k.bs.infinityscroll.model.ModelPost

class PostListVm : BaseViewModel(), ItemDeleteListener {
    val adapter = InfinityRecyclerViewAdapter<PostItemVm, ItemPostBinding>(
        R.layout.item_post,
        BR.vm
    ) { load() }

    init {
        load()
    }

    override fun itemDelete(model: ModelPost) {
        adapter.items.removeAll { it.id == model.id }
        adapter.notifyDataSetChanged()
    }

    private fun load() {
        ApiService.jsonPlaceHolder()
            .posts()
            .map { it.map { PostItemVm(it, this) } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapter.insertAll(it)
            }, {

            })
            .addTo(compositeDisposable)
    }
}