package k.bs.infinityscroll.scene.postDetail

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Rect
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import io.reactivex.subjects.SingleSubject
import k.bs.infinityscroll.R
import k.bs.infinityscroll.base.BaseBindingActivity
import k.bs.infinityscroll.databinding.ActivityPostDetailBinding
import k.bs.infinityscroll.model.ModelPost
import k.bs.infinityscroll.util.error.CanceledByUserException

class PostDetailActivity : BaseBindingActivity<ActivityPostDetailBinding, PostDetailVm>() {
    override val resId: Int = R.layout.activity_post_detail
    override val viewModel: PostDetailVm = createVm()
    lateinit var argument: ModelPost

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActionBar()
        argument = getInput()
        viewModel.init(argument)
    }

    private fun createVm() = PostDetailVm(object : PostDetailVm.Contract {
        override fun deletePost() {
            finishWithResponse(argument.apply {
                postDeleted = true
            })
        }

        override fun createEditDialog(): SingleSubject<String> {
            val edit = EditText(this@PostDetailActivity)
            val dialogSubject = SingleSubject.create<String>()
            AlertDialog.Builder(this@PostDetailActivity)
                .setTitle("수정하기")
                .setView(edit)
                .setNegativeButton("취소") { dialog, i -> dialog.cancel() }
                .setPositiveButton("확인") { dialog, i ->
                    dialogSubject.onSuccess(edit.text.toString())
                    dialog.dismiss()
                }
                .setOnCancelListener { dialogSubject.onError(CanceledByUserException()) }
                .show()

            return dialogSubject
        }
    })

    private fun setActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home)
            onBackPressed()

        return true
    }

    override fun onBackPressed() {
        finishWithResponse(argument.apply {
            title = viewModel.title.get().toString()
            body = viewModel.body.get().toString()
        })
    }
}
