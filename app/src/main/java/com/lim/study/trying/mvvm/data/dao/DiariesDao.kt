package com.lim.study.trying.mvvm.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lim.study.trying.mvvm.data.entity.DiaryEntity

//인터페이스는 구현체가 없으면 못 쓰는데 이걸 다 kapt가 만들어줌
@Dao
interface DiariesDao {
    @Query("SELECT * FROM diaryEntity")
    fun getAllDiaries(): List<DiaryEntity>

    @Query("SELECT * FROM diaryentity WHERE id = :diaryId")     // 파라미터를 이 쿼리에 인식시키는 방법 (: 적고 파라미터 이름 적기)
    fun getDiary(diaryId: String): DiaryEntity?

    @Insert
    fun insertDiary(diaryEntity: DiaryEntity)
}

/* DAO (Data Access Object)
 * 직접 데이터를 접근하는 객체
 * SQLite로 select할 때는 엄청 복잡했는데 그 많은 코드를 쓸 필요 없게 해줌
 * 코드를 kapt가 만들어줌
 * 우리는 인터페이스만 만들어주면 됨
 */