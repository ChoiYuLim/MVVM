package com.lim.study.trying.mvvm.presentation.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("app:createdDate")
fun bindMemoCreatedDate(textView: TextView, date: Date?) { //null이 들어올 수 있다
    if (date == null) return //null이 들어왔을 때 바로 처리
    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
    textView.text = simpleDateFormat.format(date)
}

//android:text= "@{diary.createDate.toString()}" 으로 하면 날짜 포맷이 너무 길게 나와서
//원하는 날짜 포맷으로 셋팅하기 위해 BindingAdapter 사용


//만약 class 내부에 선언하는 경우가 있으면 클래스 내부에 아래와 같이 쓰면 데이터바인딩이 인식을 못하고 터진다.
/*
class BindingAdapters {
    @BindingAdapter("app:createdDate")
    fun bindMemoCreatedDate(textView: TextView, date: Date?) { //null이 들어올 수 있다
        if (date == null) return //null이 들어왔을 때 바로 처리
        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
        textView.text = simpleDateFormat.format(date)
    }
}
*/

/*
class BindingAdapters {
    companion object { //엄밀히 따지면 companion object는 static이 아니므로 방지하기 위해
        @JvmStatic //static이 아닌데 최상위처럼 보이게 해주려면 어노테이션 붙여야함
        @BindingAdapter("app:createdDate")
        fun bindMemoCreatedDate(textView: TextView, date: Date?) { //null이 들어올 수 있다
            if (date == null) return //null이 들어왔을 때 바로 처리
            val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
            textView.text = simpleDateFormat.format(date)
        }
    }
}
이렇게 하면 귀찮으니깐 그냥 제일 위의 방식대로!
*/