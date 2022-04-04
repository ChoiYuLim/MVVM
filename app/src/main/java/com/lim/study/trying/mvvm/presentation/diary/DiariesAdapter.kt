package com.lim.study.trying.mvvm.presentation.diary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lim.study.trying.mvvm.databinding.ItemDiaryBinding
import com.lim.study.trying.mvvm.domain.Diary

// 1. view holder
// 2. diff util
// 3. list adapter

//DiariesAdapter의 생성자에 다이어리 클릭 콜백을 넣는 것을 강제해보자
class DiariesAdapter(
    private val onMemoClick: ((Diary) -> Unit)? = null, // 안 넣을 수도 있으니깐 안 넣으면 null이 자동으로 들어가게 default로 null 넣어주기
) : ListAdapter<Diary, DiariesAdapter.ViewHolder>(DiariesComparator()) { //처음 타입에는 item을 넣어(Diary), 그 다음 viewHolder 넣어 (nested class 이기 때문에 DiariesAdapter.ViewHolder에서 . 을 지울 수 없음)

    //원래 item 찾으려면 private val diaries = MutableListOf<Diary>() 이렇게 해서 getItem 어쩌구 이런식으로 했었는데 필요없음
    //기존의 리싸이클러뷰를 사용했다면 아이템 목록을 직접 가지고 있어야함
    //데이터 넘기는 코드가 하나도 없음
    //원래 데이터 넘겨서 diaries 추가하고 notifyItemchanged 이런거 하고 했는데
    //여기 데이터를 넘기는 방식은 submitList(list) 이게 끝
    /* override fun submitList(list: MutableList<Diary>?) {
        super.submitList(list)
    } 이걸 외부에서 호출해서 넣어주기만 하면 된다 */
    //중요한 로직들을 쓸 필요가 없다
    //데이터를 받아서 보여주는 것은 리스트 어댑터가 직접 알아서 해주기 때문에 신경 쓰지 않고 넘겨주기만 하면 된다


    //뷰홀더를 만들기 위해서는 바인딩이 필요할 거고 , 바인딩을 만들기 위해서는 layoutInflater가 필요
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context) //from 안에 context가 필요. inflater는 context로 부터 가지고 올 수 있는데 context는 뷰에 항상 들어있음. parent에 뷰 있으니깐 parent에서 꺼내서 쓰면 됨(parent.context)
        val binding = ItemDiaryBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding) //뷰홀더 리턴해야하므로
    }

    //뷰홀더에 아이템을 세팅해주는 역할
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val diary: Diary = getItem(position)// 원하는 다이어리를 가져올 수 있다 , getItem은 리스트어댑터에서 구현해놓은 메소드
        holder.bind(diary, onMemoClick) // 뷰홀더에 데이터를 넘겨줄건데 뷰홀더에 만들어놓은 bind 함수로 넣어주면 된다
    }

    //순수 recyclerview adapter로 구현했으면 getItemCount()라는 것도 구현해줬어야한다
    //listAdapter에서 getItemCount() 구현했기 때문에 우리가 만들 필요 없음

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
