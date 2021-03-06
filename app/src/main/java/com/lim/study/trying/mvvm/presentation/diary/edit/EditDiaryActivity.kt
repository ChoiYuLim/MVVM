package com.lim.study.trying.mvvm.presentation.diary.edit

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.lim.study.trying.mvvm.databinding.ActivityEditDiaryBinding

class EditDiaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditDiaryBinding

    private val editDiaryViewModel: EditDiaryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditDiaryBinding.inflate(layoutInflater)//바인딩 객체 생성됨
        // xml을 써먹으려면 뷰를 만들어야하자나 핸드폰 jvm을 쓰는데 자바 코드가 있어야한다
        //inflate할 때 xml을 파싱함
        //그래서 inflater 안에 parser가 들어있다
        setContentView(binding.root) //여기까지 데이터바인딩 쓸 준비됨
        binding.lifecycleOwner = this   // 꼭 필요한거 맞음, 까먹기 쉬움! 주의

        editDiaryViewModel.loadDiary(getDiaryId())

        binding.buttonSubmit.setOnClickListener {
            editDiaryViewModel.saveDiary()
            setResult(Activity.RESULT_OK)
            finish()
        }

        Log.d("lim", "Diary Id = ${getDiaryId()}")

        binding.viewModel = editDiaryViewModel

        /*
            editDiaryViewModel.title.observe(this){
                Log.d("yulim", "edittext changed : $it")
            }
        */
    }

    /*
    원래 이전에는 이 함수들을 썼는데 없어짐
    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
    */

    private fun getDiaryId(): String? { //다이어리 id 안 넣고 보낼 수 있음
        return intent.getStringExtra(KEY_DIARY_ID)
    }

    companion object {
        const val KEY_DIARY_ID = "KEY_DIARY_ID" //상수로 외부에서 EditDiaryActivity.KEY_DIARY_ID로 접근 가능
    }
}
