package k.bs.infinityscroll

import io.reactivex.schedulers.Schedulers
import k.bs.infinityscroll.api.ApiService
import k.bs.infinityscroll.model.ModelPatch
import org.junit.Test

class ApiTest {

    @Test
    fun getPosts() {
        ApiService.jsonPlaceHolder()
            .posts()
            .subscribeOn(Schedulers.io())
            .test()
            .await()
            .assertNoErrors()
            .assertComplete()
            .assertValueCount(1)
            .assertValueAt(0) { it.count() == 100 }
    }


    @Test
    fun getPostDetail() {
        ApiService.jsonPlaceHolder()
            .postDetail(postId = 1)
            .subscribeOn(Schedulers.io())
            .test()
            .await()
            .assertNoErrors()
            .assertComplete()
            .assertValueCount(1)
            .assertValue { it.id == 1 }
    }

    @Test
    fun getPostComment() {
        ApiService.jsonPlaceHolder()
            .postComment(postId = 1)
            .subscribeOn(Schedulers.io())
            .test()
            .await()
            .assertNoErrors()
            .assertComplete()
            .assertValueCount(1)
            .assertValue { it[0].postId == 1 }
    }

    @Test
    fun editPost() {
        ApiService.jsonPlaceHolder()
            .postEdit(
                postId = 1,
                body = ModelPatch(
                    title = "hello",
                    body = "world"
                )
            )
            .subscribeOn(Schedulers.io())
            .test()
            .await()
            .assertNoErrors()
            .assertComplete()
            .assertValue { it.title=="hello" }
            .assertValue { it.body=="world" }
    }

    @Test
    fun deletePost(){
        ApiService.jsonPlaceHolder()
            .postDelete(1)
            .subscribeOn(Schedulers.io())
            .test()
            .await()
            .assertNoErrors()
            .assertComplete()
    }
}
