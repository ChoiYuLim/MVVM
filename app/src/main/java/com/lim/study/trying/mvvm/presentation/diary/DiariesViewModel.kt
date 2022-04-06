package com.lim.study.trying.mvvm.presentation.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lim.study.trying.mvvm.data.DiaryMemory
import com.lim.study.trying.mvvm.domain.Diary

class DiariesViewModel : ViewModel(){   // ViewModel은 클래스니깐 () 필요, by viewModels()로부터 인식을 해서 알아서 뷰모델로 인식됨

    private val _diaries = MutableLiveData<List<Diary>>()
    val diaries : LiveData<List<Diary>> = _diaries  //구독 가능한 데이터를 만들었음

    // 만들어놓으면 작성하고나서 돌아오면 onresume() 호출될 때 모든 리스트 다시 갱신시켜주는데 그 때 또 한번 불러줄 수 있음
    fun loadDiaries(){
        _diaries.value = DiaryMemory.getAllDiaries()
    }

    /*
    init{
        _diaries.value = listOf()
    }   */

    /*
    override fun onCleared() {
        super.onCleared()
        //진짜로 죽었을 때 호출
    }   */
}