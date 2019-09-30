package k.bs.infinityscroll.scene.postlist

import android.content.Intent
import android.view.View
import androidx.databinding.ObservableField
import k.bs.infinityscroll.model.ModelPost
import k.bs.infinityscroll.scene.postDetail.PostDetailActivity
import k.bs.infinityscroll.util.putObject
import k.bs.infinityscroll.util.startActivityForResult

class PostItemVm(private var model: ModelPost, val listener: ItemDeleteListener) {
    val id  get() = model.id
    val title = ObservableField<String>(model.title)
    val body = ObservableField<String>(model.body)

    fun moveDetail(view: View) {
        Intent(view.context, PostDetailActivity::class.java)
            .putObject(model)
            .startActivityForResult<ModelPost>(view.context)
            .subscribe({ result ->
                if (result.postDeleted)
                    listener.itemDelete(result)
            }, {

            })
    }
}