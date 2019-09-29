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

class PostListVm : BaseViewModel() {

    val adapter =
        InfinityRecyclerViewAdapter<PostItemVm, ItemPostBinding>(
            R.layout.item_post,
            BR.vm
        ) { load() }

    init {
        load()
    }

    private fun load() {
        ApiService.jsonPlaceHolder()
            .posts()
            .map { it.map { PostItemVm(it) } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapter.insertAll(it)
            }, {

            })
            .addTo(compositeDisposable)
    }
}