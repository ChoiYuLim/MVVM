package com.lim.study.trying.mvvm.presentation.diary

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.lim.study.trying.mvvm.databinding.ActivityDiariesBinding
import com.lim.study.trying.mvvm.domain.Diary
import com.lim.study.trying.mvvm.presentation.diary.edit.EditDiaryActivity

/*  뷰에서 메모리에 접근해서 직접 데이터를 갖고 오는 것 다 없앰
    뷰는 뷰의 일만 하게!
 */

class DiariesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiariesBinding

    private lateinit var diariesAdapter: DiariesAdapter

    private lateinit var editDiaryActivityLauncher: ActivityResultLauncher<Intent>

    private val diariesViewModel: DiariesViewModel by viewModels()  //  실제로 죽었는지 아닌지 viewModels()가 알아서 새 viewModel을 줄건지, 기존의 viewModel을 줄건지 판단

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDiariesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //런처는 onstarted, oncreate 두개만 가능 생명주기에 맞춰셔 해야함 onresume은 안됨
        editDiaryActivityLauncher =
            // registerForActivityResult( ) 이 안에 1.계약서를 넣어야함  2.콜백 (액티비티가 끝나고 다시 돌아왔을 때 뭐를 호출시킬거냐)
            // 1.계약서 ->  ActivityResultContracts 안에 있음 -> 자주 사용하는 것들을 구현해놓은 구현체들이 존재하는 클래스
            // 이거 하나로 권한 관련해서 깔끔하게 해결 가능 -> 이거 말리빈 블로그에 정리되어있는 거 찾아보기
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                // 2. 콜백을 람다로 집어넣기 -> 띄운 액티비티에서 setTResult를 통해서 resultok 또는 resultcancel을 받아 -> 이 결과가 콜백으로부터 들어옴 -> ActivityResult라는 객체로 부터 튀어나옴 -> 그걸 it으로 접근 가능
                when (it.resultCode) {
                    Activity.RESULT_OK -> showToast("작성 완료!")
                    Activity.RESULT_CANCELED -> showToast("작성 취소")
                    else -> showToast("문제가 발생했습니다 : $it")
                }
            } // editDiaryActivityLauncher로부터 띄운 액티비티가 종료되면 실행되는 콜백이 이 안에 들어있다

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

        //diariesAdapter.submitList(STUB_DIARY) //어댑터에 데이터 넘겨주기

        /*
        diariesAdapter.submitList(STUB_DIARY){
            //비동기가 다 끝나고 나서 호출하고 싶은 경우에는 중괄호 안에 쓰기
        }
        */

        diariesViewModel.diaries.observe(this){
            //  람다에 리스트가 들어와 (일기 목록이 변할 때마다 일기장 목록 전체 리스트가 들어옴)
            diariesAdapter.submitList(it)
        }

        binding.buttonNewDiary.setOnClickListener{ deployEditDiaryActivity() }
    }

    override fun onResume() {
        super.onResume()
        diariesViewModel.loadDiaries()  // 리스트 화면 다시 돌아왔을 때, 전체 리스트 갱신 (뷰가 뷰모델에게 이벤트를 전달하는 것!)
    }

    //어댑터 생성하기 전에 다이어리를 눌렀을 때 동작하는 메소드
    private fun onDiaryClick(diary: Diary) { //다이어리를 인자로 받아서 Unit을 리턴하고 있는 것임
        deployEditDiaryActivity(diary)
    }

    private fun deployEditDiaryActivity(diary: Diary? = null){
        val intent = Intent(this, EditDiaryActivity::class.java)
        intent.putExtra(EditDiaryActivity.KEY_DIARY_ID, diary?.id) //diary가 null이면 .id 까지도 안 오고 바로 null로 치환해줌
        editDiaryActivityLauncher.launch(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

//    companion object {
//        private val STUB_DIARY = listOf(
//            Diary("0", "title1", "content", Date()),
//            Diary("1", "title2", "content", Date()),
//            Diary("2", "title3", "content", Date()),
//            Diary("3", "title4", "content", Date()),
//            Diary("4", "title5", "content", Date()),
//        )
//    }
}
