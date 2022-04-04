package com.lim.study.trying.mvvm.presentation.diary

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lim.study.trying.mvvm.databinding.ItemDiaryBinding
import com.lim.study.trying.mvvm.domain.Diary

// 1. view holder
// 2. diff util
// 3. list adapter

class DiariesAdapter {

    class ViewHolder(
        private val binding: ItemDiaryBinding, // 생성자로 뷰의 바인딩 클래스를 받아
    ) : RecyclerView.ViewHolder(binding.root) { // viewHolder는 리싸이클러뷰의 뷰홀더를 상속받아야함 이 클래스의 생성자에는 뷰홀더에 쓰일 뷰를 통째로 넘겨줘야하니깐 binding.root

        /* 데이터 바인딩 아용해서 다이어리 정보를 넘기게끔 xml 만들어줬음
        그럼 이 바인딩에 다이어리를 세팅해줘야함
        메소드를 통해 다이어리 정보를 묶어주게 만들자 */
        fun bind(
            diary: Diary,
            onMemoClick: ((Diary) -> Unit)? = null
        ) { // 아이템을 눌렀을 때 리스너 달아주기 , 아이템을 누르면 그 아이템에 해당하는 다이어리가 파라미터로 넘어오는 콜백함수 , 클릭했을 때 아무 일도 안 일어나게 할 수 있으니깐 nullable(?)

            binding.diary = diary
            binding.root.setOnClickListener { onMemoClick?.invoke(diary) } // 눌렀을 때 콜백 리스너를 보내주면 됨 , 지금 nullable이어서 invoke함수를 직접 호출해주는 것
            //binding.root.setOnClickListener { onMemoClick(diary) } 만약 null이 아니라면 일반 함수처럼 바로 쓸 수 있음
        }
    }

    /*다이어리라는 아이템을 고유하게 비교하게 만들어주는 diffutil 만들기
        리싸이클러뷰한테 다이어리를 이렇게 비교해! 알려주는 것 */

    private class DiariesComparator :
        DiffUtil.ItemCallback<Diary>() { // DiffUtil을 상속받을건데 Diary를 비교할거니깐 <Diary>
        override fun areItemsTheSame(oldItem: Diary, newItem: Diary): Boolean {
            return oldItem.id == newItem.id
            // Diary 아이템이 서로 고유하게 다른지 같은지 체크하기 위해 areItemsSame을 먼저 호출하고 이게 true가 나오면 areContentsTheSame을 호출해서 또 비교를 함
            // 두 아이템이 id로써 고유하게 같은데 내용이 다를 수가 있으니깐 그걸 비교하기 위해서
        }

        override fun areContentsTheSame(oldItem: Diary, newItem: Diary): Boolean {
            return oldItem == newItem
            //areItemsTheSame이 false이면 호출되지 않는다
        }
    }
}
