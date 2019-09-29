package k.bs.infinityscroll.api.service

import io.reactivex.Completable
import io.reactivex.Single
import k.bs.infinityscroll.model.ModelComment
import k.bs.infinityscroll.model.ModelPatch
import k.bs.infinityscroll.model.ModelPost
import retrofit2.http.*

interface ApiJsonPlaceHolder {
    @GET("/posts")
    fun posts(): Single<List<ModelPost>>

    @GET("/posts/{postId}")
    fun postDetail(@Path("postId") postId: Int): Single<ModelPost>

    @GET("/posts/{postId}/comments")
    fun postComment(
        @Path("postId") postId: Int,
        @Query("postId") filter: Int = postId
    ): Single<List<ModelComment>>


    @PATCH("posts/{postId}")
    @Headers("Content-Type:application/json", "charset:utf-8")
    fun postEdit(
        @Path("postId") postId: Int,
        @Body body: ModelPatch
    ): Single<ModelPost>

    @DELETE("posts/{postId}")
    fun postDelete(@Path("postId") postId: Int): Completable
}