package k.bs.infinityscroll.scene.postDetail

import k.bs.infinityscroll.model.ModelComment

class CommentItemVm(val model: ModelComment) {
    val name get() = model.name
    val body get() = model.body
}