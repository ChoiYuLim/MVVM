package com.lim.study.trying.mvvm.presentation.diary.edit

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
//  import com.lim.study.trying.mvvm.BR
import com.lim.study.trying.mvvm.databinding.ActivityEditDiaryBinding
import com.lim.study.trying.mvvm.domain.Diary
import java.util.*

class EditDiaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditDiaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditDiaryBinding.inflate(layoutInflater)//바인딩 객체 생성됨
        // xml을 써먹으려면 뷰를 만들어야하자나 핸드폰 jvm을 쓰는데 자바 코드가 있어야한다
        //inflate할 때 xml을 파싱함
        //그래서 inflater 안에 parser가 들어있다
        setContentView(binding.root) //여기까지 데이터바인딩 쓸 준비됨

        binding.buttonSubmit.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}
