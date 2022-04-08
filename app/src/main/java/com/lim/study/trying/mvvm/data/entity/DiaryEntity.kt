package com.lim.study.trying.mvvm.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity     // room한테 이게 Entity라고 알려주는 것
data class DiaryEntity (
    val title: String,
    val content: String,
    val createdDate: Date,
    @PrimaryKey     // 이게 Primary Key라고 room한테 알려줘야함
    val id: String = UUID.randomUUID().toString(),     // room에 객체를 저장하려면 반드시 id가 있어야한다 (엔티티를 고유하게 식별할 Primary Key가 존재해야함)
)

/* DB에는 Date라는 자바 객체를 저장할 수 없음
 * 숫자, 문자열밖에 저장을 못 함
 * 자바 객체를 DB에 저장할 수 있는 형태로 바꿔주는 type converter라는 것을 만들어줄 것 -> DateTypeConverter.kt
 */