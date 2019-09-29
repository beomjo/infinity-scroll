package k.bs.infinityscroll.scene.postDetail

import android.os.Bundle
import k.bs.infinityscroll.R
import k.bs.infinityscroll.base.BaseBindingActivity
import k.bs.infinityscroll.databinding.ActivityPostDetailBinding

class PostDetailActivity : BaseBindingActivity<ActivityPostDetailBinding, PostDetailVm>() {
    override val resId: Int = R.layout.activity_post_detail
    override val viewModel: PostDetailVm = PostDetailVm()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(getInput())
    }
}
