package com.lim.study.trying.mvvm.data

import com.lim.study.trying.mvvm.domain.Diary
import java.util.*

/* 객체는 영속성 DB가 아니라서 앱을 껐다 키면 저장 안됨 */
object DiaryMemory {
    private val diaries: MutableMap<String, Diary> = mutableMapOf(
        "first" to Diary("first", "기본 메모", "그냥 들어있는 메모", Date())
    )

    fun saveDiary(diary: Diary){
        diaries[diary.id]= diary
    }

    fun getDiary(id:String): Diary {
        return diaries[id] ?: error("cannot find diary id of $id")
    }

    fun getAllDiaries(): List<Diary>{
        return diaries.map {it.value}
    }

}