package kr.dori.android.practicesqlite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.dori.android.practicesqlite.databinding.ItemRecyclerBinding
import java.text.SimpleDateFormat

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.Holder>() {
    var helper: SqliteHelper? = null
    val listData = mutableListOf<Memo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val memo = listData.get(position)
        holder.setMemo(memo)
    }

    override fun getItemCount(): Int {
        return listData.size
    }
    inner class Holder(val binding: ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root){
        init{
            binding.buttonDelete.setOnClickListener{
                helper?.deleteMemo(mMemo!!) //SQLite 데이터를 먼저 삭제
                listData.remove(mMemo)
                notifyDataSetChanged()
            }
        }
        var mMemo: Memo? = null //클릭 시점에 어떤 데이터가 있는지 알기 위해서 설정한 변수

        fun setMemo(memo: Memo){
            binding.textNo.text = "${memo.no}"
            binding.textContent.text = memo.content
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            binding.textDatetime.text="${sdf.format(memo.datetime)}"

            this.mMemo = memo //이렇게 포지션을 받아와서
        }
    }
}





