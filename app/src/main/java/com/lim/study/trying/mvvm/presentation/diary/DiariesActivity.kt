package com.lim.study.trying.mvvm.presentation.diary

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lim.study.trying.mvvm.databinding.ActivityDiariesBinding
import com.lim.study.trying.mvvm.domain.Diary
import java.util.*

class DiariesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiariesBinding

    private lateinit var diariesAdapter: DiariesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDiariesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //DiariesAdapter() 여기 안에 람다를 넣으면된다, 무슨 일이 일어날거냐
        diariesAdapter = DiariesAdapter {
            onDiaryClick(it) //람다에 다이어리를 파라미터로 넣게끔 만들어놨음 그래서 it 넣으면 됨
        }

        /*
        diariesAdapter = DiariesAdapter(this::onDiaryClick) //이 액티비티 안에 있는 함수를 그대로 넘기겠다!
        diariesAdapter = DiariesAdapter(::onDiaryClick) // this 생략 가능
        diariesAdapter = DiariesAdapter({onDiaryClick(it)}) //이렇게는 거의 안 씀
        diariesAdapter = DiariesAdapter{onDiaryClick(it)} // 이렇게 가장 많이 씀
        네가지 전부 가능 (같은 코드)
        */

        //어댑터를 만들었으니 리싸이클러뷰에다가 세팅해줘야함
        binding.listDiaries.adapter = diariesAdapter

        diariesAdapter.submitList(STUB_DIARY) //어댑터에 데이터 넘겨주기

        /*
        diariesAdapter.submitList(STUB_DIARY){
            //비동기가 다 끝나고 나서 호출하고 싶은 경우에는 중괄호 안에 쓰기
        }
        */
    }

    //어댑터 생성하기 전에 다이어리를 눌렀을 때 동작하는 메소드
    private fun onDiaryClick(diary: Diary) { //다이어리를 인자로 받아서 Unit을 리턴하고 있는 것임
        Log.d("lim", "Diary : $diary")
        Toast.makeText(this, diary.toString(), Toast.LENGTH_LONG).show()
    }

    companion object {
        private val STUB_DIARY = listOf(
            Diary(0, "title1", "content", Date()),
            Diary(1, "title2", "content", Date()),
            Diary(2, "title3", "content", Date()),
            Diary(3, "title4", "content", Date()),
            Diary(4, "title5", "content", Date()),
        )
    }
}
