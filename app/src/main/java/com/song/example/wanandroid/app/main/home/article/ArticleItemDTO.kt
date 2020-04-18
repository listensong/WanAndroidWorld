package com.song.example.wanandroid.app.main.home.article

import com.squareup.moshi.Json

data class ArticleItemDTO(

		@Json(name="shareDate")
		val shareDate: Long? = null,

		@Json(name="projectLink")
		val projectLink: String? = null,

		@Json(name="prefix")
		val prefix: String? = null,

		@Json(name="canEdit")
		val canEdit: Boolean? = null,

		@Json(name="origin")
		val origin: String? = null,

		@Json(name="link")
		val link: String? = null,

		@Json(name="title")
		val title: String? = null,

		@Json(name="type")
		val type: Int? = null,

		@Json(name="selfVisible")
		val selfVisible: Int? = null,

		@Json(name="apkLink")
		val apkLink: String? = null,

		@Json(name="envelopePic")
		val envelopePic: String? = null,

		@Json(name="audit")
		val audit: Int? = null,

		@Json(name="chapterId")
		val chapterId: Int? = null,

		@Json(name="id")
		val id: Int? = null,

		@Json(name="courseId")
		val courseId: Int? = null,

		@Json(name="superChapterName")
		val superChapterName: String? = null,

		@Json(name="descMd")
		val descMd: String? = null,

		@Json(name="publishTime")
		val publishTime: Long? = null,

		@Json(name="niceShareDate")
		val niceShareDate: String? = null,

		@Json(name="visible")
		val visible: Int? = null,

		@Json(name="niceDate")
		val niceDate: String? = null,

		@Json(name="author")
		val author: String? = null,

		@Json(name="zan")
		val zan: Int? = null,

		@Json(name="chapterName")
		val chapterName: String? = null,

		@Json(name="userId")
		val userId: Int? = null,

		@Json(name="tags")
		val tags: List<Tag>? = null,

		@Json(name="superChapterId")
		val superChapterId: Int? = null,

		@Json(name="fresh")
		val fresh: Boolean? = null,

		@Json(name="collect")
		val collect: Boolean? = null,

		@Json(name="shareUser")
		val shareUser: String? = null,

		@Json(name="desc")
		val desc: String? = null
)