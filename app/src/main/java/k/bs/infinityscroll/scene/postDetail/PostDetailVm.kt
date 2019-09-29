package k.bs.infinityscroll.scene.postDetail

import androidx.databinding.ObservableField
import k.bs.infinityscroll.base.BaseViewModel
import k.bs.infinityscroll.model.ModelPost

class PostDetailVm : BaseViewModel() {
    val title =ObservableField<String>()
    val body =ObservableField<String>()

    fun init(model :ModelPost){
        title.set(model.title)
        body.set(model.body)
    }

}