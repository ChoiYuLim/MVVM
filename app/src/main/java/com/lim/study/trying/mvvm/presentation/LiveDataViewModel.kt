package com.lim.study.trying.mvvm.presentation

import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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

//  val count = ObservableInt()

    private val _stateFlow = MutableStateFlow<Int>(0)   // 라이브 데이터와 가장 큰 차이점 : 반드시 초기값 넣어줘야함
    val stateFlow: StateFlow<Int> = _stateFlow
    // 이렇게 하면 라이브데이터랑 똑같아진다

    init {
//      _count.value = 89
//      val count = _count.value ?: 0   // nullable 하기 때문에 null 처리 유의

//      이것은 실험을 위한 것 , 근본적으로 Handler나 HandlerThread로 구현해야함
        Thread {
            while (true) {
                Thread.sleep(1_000)
                _stateFlow.value = _stateFlow.value + 1
                /* liveData는 숨겨지면 알아서 호출안되는데 얘는 그런거 없음 hold 눌러도 그대로 동작함 (내부적으로 안해주는거지 우리가 직접 구현해줘야함) */

                // count.set(count.get() + 1)
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


    /*리액티브 프로그래밍에서 사용하는 스트림이라는 개념이 있음
    * 스트림을 코틀린 코루틴에서 구현해놓은게 플로우라는 애가 있는 거
    * RX에서는 옵저버블, 플로어블이라는 말도 쓰고 이름이 다 다름
    * 리액티브 프로그래밍에 스트림이라는 말을 쓰면
    * 그 스트림은 두가지 방법이 있음
    * 콜드 스트림, 핫 스트림
    * 스트림은 구독 가능한 형태 , 뭔가 데이터를 계속 쏠거야 1,2,3,4,...
    * 이걸 누군가 observe할 때 콜드 스트림은 이 친구가 방출한 처음부터 끝까지 다 가져옴 (3이후에 구독 시작! 해도 0,1,2,3,4,... 다 들어옴)
    * 핫 스트림은 구독하는 시점부터 구독하기 시작 (3 이후에 구독 시작! 하면 4,... 들어옴
    * 코틀린 코루틴의 플로우는 콜드 스트림 근데 우리는 콜드 스트림을 쓰면 안됨
    * 라이브데이터랑 비슷한 stateFlow라는게 나왔음
    * stateFlow도 핫 스트림
    * 기존 콜드 스트림인 그냥 플로우를 stateFlow로 바꿀 수도 있는데 직접 공부해보기
    * 그냥 라이브데이터처럼 사용하는 거
    * */
}