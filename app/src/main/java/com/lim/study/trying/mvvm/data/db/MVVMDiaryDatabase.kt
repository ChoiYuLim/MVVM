package com.lim.study.trying.mvvm.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lim.study.trying.mvvm.data.converter.DateTypeConverter
import com.lim.study.trying.mvvm.data.dao.DiariesDao
import com.lim.study.trying.mvvm.data.entity.DiaryEntity

/* DAO 객체 인터페이스 만들었잖아, 이제 데이터베이스로부터 DAO의 구현체를 가져올 수 있게 만들어줌 */
/* interface가 아니라 abstract로 선언하는 이유는 상속받는 RoomDatabase()가 클래스여서! (인터페이스는 abstract class를 상속받을 수 없으니깐) */

// Database 어노테이션 안에는 어떤 엔티티 클래스들을 사용하는가 알려줘야함, 버전도 알려줘야함
@Database(
    entities = [DiaryEntity::class], version = 1
)

// 어떤 타입컨버터들을 쓸거냐, 아 Date 타입을 Long으로 바꾸면 되는구나 룸이 알게됨
@TypeConverters(DateTypeConverter::class)

abstract class MVVMDiaryDatabase: RoomDatabase() {

    abstract fun getDariesDao(): DiariesDao
}