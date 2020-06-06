package com.song.example.study.wanandroid.network

import com.song.example.study.common.network.retrofit.CoroutineLifecycleCall
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author: Listensong
 * @time 19-10-24 下午3:33
 * @desc com.song.example.study.wanandroid.network.WanService
 */
interface WanService {
    //首页
    @GET("article/list/{pageNum}/json")
    fun getArticleList(
            @Path("pageNum") pageNum: Int = 0
    ): CoroutineLifecycleCall<ResponseBody>

    @GET("article/top/json")
    fun getTopArticles(): CoroutineLifecycleCall<ResponseBody>

    @GET("banner/json")
    fun getBannerList(): CoroutineLifecycleCall<ResponseBody>

    //体系
    @GET("tree/json")
    fun getKnowledgeTreeTags(): CoroutineLifecycleCall<ResponseBody>

    @GET("article/list/{pageNum}/json")
    fun getKnowledgeArticles(
            @Path("pageNum") pageNum: Int = 0,
            @Query("cid") articlesCid: Int
    ): CoroutineLifecycleCall<ResponseBody>

    @GET("article/list/{pageNum}/json")
    fun searchArticlesByAuthor(
            @Path("pageNum") pageNum: Int = 0,
            @Query("author") articlesAuthor: String
    ): CoroutineLifecycleCall<ResponseBody>

    //项目
    @GET("article/listproject/{pageNum}/json")
    fun getProjectArticles(
            @Path("pageNum") pageNum: Int = 0
    ): CoroutineLifecycleCall<ResponseBody>

    //广场
    @GET("user_article/list/{pageNum}/json")
    fun getSquareArticles(
            @Path("pageNum") pageNum: Int = 0
    ): CoroutineLifecycleCall<ResponseBody>

    //搜索
    @GET("hotkey/json")
    fun getSearchHotKey(): CoroutineLifecycleCall<ResponseBody>

    @POST("article/query/{pageNum}/json")
    fun queryKeyWord(
            @Path("pageNum") pageNum: Int = 0,
            @Query("k") keyWord: String
    ): CoroutineLifecycleCall<ResponseBody>

    //常用网站
    @GET("friend/json")
    fun getFriendWebSite(): CoroutineLifecycleCall<ResponseBody>

    //项目分类
    @GET("project/tree/json")
    fun getProjectCategory(): CoroutineLifecycleCall<ResponseBody>

    //项目分类
    @GET("project/list/{pageNum}/json")
    fun getProjectCategoryArticles(
            @Path("pageNum") pageNum: Int = 0,
            @Query("cid") categoryId: Int
    ): CoroutineLifecycleCall<ResponseBody>
}