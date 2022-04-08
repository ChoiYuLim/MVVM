package com.lim.study.trying.mvvm.data.converter

import androidx.room.TypeConverter
import java.util.*

class DateTypeConverter {

    @TypeConverter     // 룸에 이게 엔티티에 있는 타입을 변경시켜주는 typeconverter라는 것을 알려줘야한다
    fun fromDate(date: Date?): Long? {       // DB칸에 날짜가 없을 수도 있음 -> nullable (Date?) , 그래서 반환값도 nullable (Long?)
        return date?.time
    }

    @TypeConverter
    fun toDate(millisecond: Long?): Date? {
        return Date(millisecond ?: return null)
    }
}

/* DB에 저장할 수 있는 형태인 숫자로 변경할 것
 * Date는 숫자로 변경할 때 밀리세컨드를 꺼낼 수 있다,
 * 그걸 long으로 저장하고 다시 꺼내서 Date로 역변환을 해주는 컨버터를 만들 것
 */