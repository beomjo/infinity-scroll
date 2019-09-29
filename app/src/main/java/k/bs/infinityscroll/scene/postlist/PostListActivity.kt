package k.bs.infinityscroll.scene.postlist

import k.bs.infinityscroll.R
import k.bs.infinityscroll.base.BaseBindingActivity
import k.bs.infinityscroll.databinding.ActivityPostListBinding

class PostListActivity : BaseBindingActivity<ActivityPostListBinding, PostListVm>() {
    override val resId: Int = R.layout.activity_post_list
    override val viewModel: PostListVm = PostListVm()
}
