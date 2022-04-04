package com.lim.study.trying.mvvm.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lim.study.trying.mvvm.databinding.ActivityLiveDataTestBinding

class LiveDataTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLiveDataTestBinding

    private val liveDataTest = LiveDataViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLiveDataTestBinding.inflate(layoutInflater)
        binding.liveDataTest = liveDataTest
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
    }
}