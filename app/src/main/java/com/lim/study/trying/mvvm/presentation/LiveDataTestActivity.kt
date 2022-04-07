package com.lim.study.trying.mvvm.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.Observable
import androidx.databinding.ObservableInt
import com.lim.study.trying.mvvm.databinding.ActivityLiveDataTestBinding
import java.util.*

class LiveDataTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLiveDataTestBinding

//  private val liveDataTest = LiveDataViewModel()
    private val liveDataViewModel: LiveDataViewModel by viewModels()    // 단순하게 뷰모델을 주입받는 것은 생성자에 아무것도 없어야한다 (LiveDataViewModel에 생성자 아무것도 없다)
//  private val liveDataViewModel by viewModels<LiveDataViewModel>() 위랑 같은 말

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLiveDataTestBinding.inflate(layoutInflater)
        this.binding = binding
        binding.liveDataViewModel = liveDataViewModel
        binding.lifecycleOwner = this   // 라이프사이클 오너는 액티비티가 갖고 있기 떄문에 this

        /* 데이터 바인딩의 바인딩 객체를 만드는 방법 2가지
        1. xml로부터 자동으로 생성된 바인딩 객체로부터 inflate 시켜서 만드는 방법
        2. DataBindingUtil을 사용하는 방법 (부모는 자식을 모름 타입을 모를 때 쓰는거)
        */

        setContentView(binding.root)
/*
        liveDataTest.count.observe(this){
            Log.d("lim", "count = $it")
        }
*/

        /* liveData는 라이프사이클이 합류되어있지만 Observable은 라이프사이클 합류되어있지 않아서 직접 꺼주고 켜주고 해야함 */
        liveDataViewModel.count.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback(){    // 직접 인터페이스로 구현해줘야함, OnPropertyChangedCallback은 클래스라서 () 필요
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                sender as ObservableInt // 캐스팅 해줘야 함
                sender.get()    // count 가져옴
                Log.d("lim", "count = $(sender.get())")
            }
        })
    }
}