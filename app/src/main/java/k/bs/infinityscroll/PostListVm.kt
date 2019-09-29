package k.bs.infinityscroll

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import k.bs.infinityscroll.api.ApiService
import k.bs.infinityscroll.databinding.BaseRecyclerViewAdapter
import k.bs.infinityscroll.databinding.ItemPostBinding

class PostListVm {
    val adapter = BaseRecyclerViewAdapter<PostItemVm, ItemPostBinding>(R.layout.item_post, BR.vm)

    init {
        ApiService.jsonPlaceHolder()
            .posts()
            .map { it.map { PostItemVm(it) } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapter.replaceAll(it)
            }, {

            })
    }
}