package k.bs.infinityscroll.scene.postDetail

import android.os.Bundle
import k.bs.infinityscroll.R
import k.bs.infinityscroll.base.BaseBindingActivity
import k.bs.infinityscroll.databinding.ActivityPostDetailBinding
import k.bs.infinityscroll.model.ModelPost

class PostDetailActivity : BaseBindingActivity<ActivityPostDetailBinding, PostDetailVm>() {
    override val resId: Int = R.layout.activity_post_detail
    override val viewModel: PostDetailVm = createVm()
    lateinit var argument: ModelPost

    private fun createVm() = PostDetailVm(object : PostDetailVm.Contract {
        override fun delelePost() {
            finishWithResponse(argument.apply {
                postDeleted = true
            })
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        argument = getInput()
        viewModel.init(argument)
    }

}
