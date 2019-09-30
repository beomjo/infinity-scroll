package k.bs.infinityscroll.scene.postDetail

import androidx.databinding.ObservableField
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.SingleSubject
import k.bs.infinityscroll.BR
import k.bs.infinityscroll.R
import k.bs.infinityscroll.api.ApiService
import k.bs.infinityscroll.base.BaseViewModel
import k.bs.infinityscroll.databinding.BaseRecyclerViewAdapter
import k.bs.infinityscroll.databinding.ItemCommentBinding
import k.bs.infinityscroll.model.ModelPatch
import k.bs.infinityscroll.model.ModelPost

class PostDetailVm(private val contract: Contract) : BaseViewModel() {
    interface Contract {
        fun deletePost()
        fun createEditDialog(): SingleSubject<String>
    }

    val title = ObservableField<String>()
    val body = ObservableField<String>()
    val commentAdapter =
        BaseRecyclerViewAdapter<CommentItemVm, ItemCommentBinding>(R.layout.item_comment, BR.vm)
    private var postId = 0

    fun init(model: ModelPost) {
        title.set(model.title)
        body.set(model.body)

        postId = model.id
        loadComment()
    }

    private fun loadComment() {
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

    fun editTitle() {
        contract.createEditDialog()
            .patchData(type = TITLE)
            .subscribe({ result ->
                title.set(result.title)
            }, {
            })
            .addTo(compositeDisposable)
    }

    fun editBody() {
        contract.createEditDialog()
            .observeOn(Schedulers.io())
            .patchData(type = BODY)
            .subscribe({ result ->
                body.set(result.body)
            }, {
            })
            .addTo(compositeDisposable)
    }

    private fun Single<String>.patchData(type: String): Single<ModelPost> {
        return this.observeOn(Schedulers.io())
            .flatMap { dialogResult ->
                ApiService.jsonPlaceHolder()
                    .postEdit(
                        postId = postId,
                        body = createPatchModel(dialogResult, type)
                    )
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun createPatchModel(dialogResult: String, type: String): ModelPatch {
        return if (type == BODY)
            ModelPatch(
                body = dialogResult,
                title = title.get().toString()
            )
        else
            ModelPatch(
                body = body.get().toString(),
                title = dialogResult
            )
    }

    fun postDeleteBtn() {
        ApiService.jsonPlaceHolder()
            .postDelete(postId = postId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                contract.deletePost()
            }, {

            })
            .addTo(compositeDisposable)
    }

    companion object {
        const val TITLE = "title"
        const val BODY = "body"
    }
}