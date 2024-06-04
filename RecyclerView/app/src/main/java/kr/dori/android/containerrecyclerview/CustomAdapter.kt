package kr.dori.android.containerrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.dori.android.containerrecyclerview.databinding.ItemRecyclerBinding
import java.text.SimpleDateFormat

class CustomAdapter: RecyclerView.Adapter<Holder>() { //상속을 받는다 -> 홀더 타입을 홀더로 지정
    var listData = mutableListOf<Memo>() //이 어댑터에서 사용할 데이터 목록 변수를 선언하기 빈 리스트는 항상 생성과 동시에 타입 명시

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder { //아이템 레이아웃을 생성하는 메서드
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false) //item_recycler.xml을 바인딩 시키기
        return Holder(binding)
    }

    override fun getItemCount(): Int{ // 리사이클러뷰에서 사용할 데이터의 총 개수를 리턴하는 메소드
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) { //생성된 뷰홀더를 화면에 보여주는 메서드
        val memo = listData.get(position)
        holder.setMemo(memo)
    }
}
//item_recycler.xml -> 부모 클래스 -> 이 클래스로 상속되어서 binding변수에 저장이 된다. -> 이것은 데이터 입력하기 전에 선행됨
class Holder(val binding: ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root){ //어댑터가 생성한 바인딩 레이아웃을 Holder에서 받아서 부모 클래스에 전달한다.
    init{
        binding.root.setOnClickListener{
            Toast.makeText(binding.root.context,"클릭된 아이템 = ${binding.textTitle.text}",Toast.LENGTH_LONG).show()
        }
    }
    fun setMemo(memo: Memo){ //어댑터에서 제공받은 레이아웃과 데이터를 여기서 합치는 것!
        binding.textNo.text = "${memo.no}" // 앱이 실행되면 MainAcitivty에 loaddata()가 실행되어 data에 데이터가 저장됨 -> listdata에 데이터 이동setMemo를 통해서 Holder class에서도 사용 가능

        var sdf = SimpleDateFormat("MM/dd hh:mm:ss")
        var formattedDate = sdf.format(memo.timeStamp)
        binding.textDate.text = formattedDate

        binding.textTitle.text = "이것이 안드로이드다 ${memo.no}"
        Glide.with(itemView).load(memo.image).into(binding.imageView)

    }
}