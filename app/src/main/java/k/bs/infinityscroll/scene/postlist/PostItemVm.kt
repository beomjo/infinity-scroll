package k.bs.infinityscroll.scene.postlist

import k.bs.infinityscroll.model.ModelPatch
import k.bs.infinityscroll.model.ModelPost

class PostItemVm(private val model: ModelPost) {
    val title get() = model.title
    val body get() = model.body

}