package com.lim.study.trying.mvvm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

abstract class MVVMDiaryDatabase : RoomDatabase() {

    abstract fun getDariesDao(): DiariesDao

    /* 데이터베이스 만들었으니깐 써먹을 수 있게끔 구현체 만들기
     * 객체가 없으니깐 꺼낼 수가 없으니깐 데이터베이스 객체 만들자
     * 쉽게 꺼내 쓸 수 있게 만드려고 싱글톤 객체로 만들기 (companion object)
     */

    companion object {
        @JvmStatic
        fun newInstance(context: Context): MVVMDiaryDatabase {      // 자기 자신을 넣는 것
            return Room.databaseBuilder(
                context,        // context가 필요한데 가져올 방법이 없어서 파라미터로 가져오기
                MVVMDiaryDatabase::class.java,      // 지금 만들고자하는 데이터베이스에 대한 클래스
                "MVVMDiaryDatabase"     // 데이터 베이스 파일명 (구분하기 위해)
            ).allowMainThreadQueries()          // 임시로 붙인 코드, 반드시 지워야하는 코드
                .build()        //이제 구현체를 쓸 수 있게 된 것!
        }
    }

    /* DB: 파일 입출력이니깐 시간이 오래 걸림, 근데 UI가 동작하는 스레드에서 DB 동작을 하게 되면 안됨! UI가 엄청 끊길 것임
     * 그래서 룸은 메인스레드에서 DB 동작하면 바로 터지게 만들어져있다 백그라운드에만 돌려야함
     * 시험용으로 쓸거니깐 메인스레드에서 동작하게 해줘! 알려주자 -> .allowMainThreadQueries() -> 메인스레드에서 터지지않음
     */
}