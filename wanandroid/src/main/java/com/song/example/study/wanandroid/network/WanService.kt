package com.song.example.study.wanandroid.network

import com.song.example.study.common.network.retrofit.ILifecycleCall
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
    ): ILifecycleCall<ResponseBody>

    @GET("article/top/json")
    fun getTopArticles(): ILifecycleCall<ResponseBody>

    @GET("banner/json")
    fun getBannerList(): ILifecycleCall<ResponseBody>

    //体系
    @GET("tree/json")
    fun getKnowledgeTreeTags(): ILifecycleCall<ResponseBody>

    @GET("article/list/{pageNum}/json")
    fun getKnowledgeArticles(
            @Path("pageNum") pageNum: Int = 0,
            @Query("cid") articlesCid: Int
    ): ILifecycleCall<ResponseBody>

    @GET("article/list/{pageNum}/json")
    fun searchArticlesByAuthor(
            @Path("pageNum") pageNum: Int = 0,
            @Query("author") articlesAuthor: String
    ): ILifecycleCall<ResponseBody>

    //项目
    @GET("article/listproject/{pageNum}/json")
    fun getProjectArticles(
            @Path("pageNum") pageNum: Int = 0
    ): ILifecycleCall<ResponseBody>

    //广场
    @GET("user_article/list/{pageNum}/json")
    fun getSquareArticles(
            @Path("pageNum") pageNum: Int = 0
    ): ILifecycleCall<ResponseBody>

    //搜索
    @GET("hotkey/json")
    fun getSearchHotKey(): ILifecycleCall<ResponseBody>

    @POST("article/query/{pageNum}/json")
    fun queryKeyWord(
            @Path("pageNum") pageNum: Int = 0,
            @Query("k") keyWord: String
    ): ILifecycleCall<ResponseBody>

    //常用网站
    @GET("friend/json")
    fun getFriendWebSite(): ILifecycleCall<ResponseBody>

    //项目分类
    @GET("project/tree/json")
    fun getProjectCategory(): ILifecycleCall<ResponseBody>

    //项目分类
    @GET("project/list/{pageNum}/json")
    fun getProjectCategoryArticles(
            @Path("pageNum") pageNum: Int = 0,
            @Query("cid") categoryId: Int
    ): ILifecycleCall<ResponseBody>
}