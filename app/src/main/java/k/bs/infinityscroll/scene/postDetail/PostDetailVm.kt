package k.bs.infinityscroll.scene.postDetail

import androidx.databinding.ObservableField
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import k.bs.infinityscroll.BR
import k.bs.infinityscroll.R
import k.bs.infinityscroll.api.ApiService
import k.bs.infinityscroll.base.BaseViewModel
import k.bs.infinityscroll.databinding.BaseRecyclerViewAdapter
import k.bs.infinityscroll.databinding.ItemCommentBinding
import k.bs.infinityscroll.model.ModelPost

class PostDetailVm : BaseViewModel() {
    val title = ObservableField<String>()
    val body = ObservableField<String>()
    val commentAdapter =
        BaseRecyclerViewAdapter<CommentItemVm, ItemCommentBinding>(R.layout.item_comment, BR.vm)

    fun init(model: ModelPost) {
        title.set(model.title)
        body.set(model.body)

        loadComment(model.id)
    }

    private fun loadComment(postId: Int) {
        ApiService.jsonPlaceHolder()
            .postComment(postId = postId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ comments ->
                commentAdapter.insertAll(comments.map { CommentItemVm(it) })
            }, {

            })
            .addTo(compositeDisposable)
    }

}