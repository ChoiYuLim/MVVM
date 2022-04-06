package com.lim.study.trying.mvvm.presentation.diary.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lim.study.trying.mvvm.data.DiaryMemory
import com.lim.study.trying.mvvm.domain.Diary
import java.lang.IllegalStateException
import java.util.*

class EditDiaryViewModel: ViewModel() {

    // 제목
//    private val _title = MutableLiveData<String>()
//    val title: LiveData<String> = _title
    val title = MutableLiveData<String>("")   // 외부에서 접근 가능하고 여기서 읽고 쓰기 가능하게 변경

    // 내용
    val content = MutableLiveData<String>("")

    // 쓴 날짜
    private val _createDate = MutableLiveData<Date>(Date())    // 디폴트 값 현재 Date 넣기
    val createDate: LiveData<Date> = _createDate

    fun loadDiary(diaryId : String?){   // 화면 두 가지 경우를 같이 씀 id값이 null일 수도 있음
        val diary = DiaryMemory.getDiary(diaryId ?: return)
        title.value = diary.title
        content.value = diary.content
        _createDate.value = diary.createDate
        //  모델로부터 아이디에 대한 다이어리 가져왔고, 그걸 라이브데이터에 세팅해준거
    }

    fun saveDiary(){
        /* edit text 알아서 MutableLiveData에 저장되는 거 그대로 가져오면 된다! */

        //  val title = this.title.value ?: ""
        val title = this.title.value.orEmpty() // 코틀린 확장 함수 orEmpty(): nullable이면 empty 반환해줌
        val content = this.content.value.orEmpty()  // this는 이 클래스를 의미
        val createDate = this.createDate.value ?: throw IllegalStateException("create date cannot be null") //exception을 날려준다
        // val createDate = this.createDate.value ?: Date()

        val diary = Diary(
            title = title,
            content = content,
            createDate = createDate,
            id = UUID.randomUUID().toString()
        )
        DiaryMemory.saveDiary(diary)
    }
 /*
 위의 내용을 이렇게 줄일 수 있음 같은 말
    fun saveDiary(){
        DiaryMemory.saveDiary(Diary(
            title = this.title.value.orEmpty(),
            content = this.content.value.orEmpty(),
            createDate = this.createDate.value ?: throw IllegalStateException("create date cannot be null"),
            id = UUID.randomUUID().toString()
        ))
 */

}