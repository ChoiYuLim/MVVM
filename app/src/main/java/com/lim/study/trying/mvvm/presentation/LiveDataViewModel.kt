package com.lim.study.trying.mvvm.presentation

import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/*
*   라이브 데이터가 변경될 뿐 액티비티에 있는 코드를 조작한 적이 없음
*   뷰모델이 바뀌었을 때만 알려주기만 함 observe를 통해서 뷰가 뷰모델 바뀐 것만 알 수 있음
*   뷰모델은 구독 가능한 형태를 가지고 있고 상태를 모델로부터 갖고 와서 상태 변경
*/

class LiveDataViewModel: ViewModel() {
//  val readWriteLiveData = MutableLiveData<String>()
//  val readOnlyLiveData: LiveData<String>

//  val count = MutableLiveData<Int>()    // private이 아니므로 외부에서 접근가능 이 값을 변경시킬 수도 있음 실수 방지하기 위해 상태 수정은 이 클래스 내부에서만 하고 외부에서는 읽을 수만 있게 만들면 아래와 같음
//  private val _count = MutableLiveData<Int>()  // 내부에서만 변경 가능하고 외부에서는 변경 불가능한 형태를 외부에 노출시키고 싶을 때 이렇게 사용!
//  val count: LiveData<Int> = _count
    /*
    val count : LiveData<Int>
        get() = _count
    */

    val count = ObservableInt()

    init {
//      _count.value = 89
//      val count = _count.value ?: 0   // nullable 하기 때문에 null 처리 유의

//      이것은 실험을 위한 것 , 근본적으로 Handler나 HandlerThread로 구현해야함
        Thread {
            while (true) {
                Thread.sleep(1_000)
                count.set(count.get() + 1)
                // val currentCount = _count.value ?: 0
//              _count.value = currentCount + 1 //이렇게 하면 mainThread에서 변경한게 아니라서 바로 터진다

                /* 방법 1 postValue */
                // _count.postValue(currentCount + 1)  // liveData 에서 제공

                /* 방법 2 MainLooper에서
                Handler(Looper.getMainLooper()).post{
                    _count.value = currentCount + 1   // value 변경은 꼭 mainThread 에서 해야한다
                } */
            }
        }.start()
    }
}