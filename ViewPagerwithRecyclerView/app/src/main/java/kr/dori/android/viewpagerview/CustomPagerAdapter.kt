package kr.dori.android.viewpagerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.dori.android.viewpagerview.databinding.ItemViewpagerBinding

class CustomPagerAdapter: RecyclerView.Adapter<Holder>() {
    var textList = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder { //layout 생성해서 Holder에게 전달해주기
        val binding = ItemViewpagerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) { //화면에 보여주는 함수
        val text = textList[position] //position값이 파라미터이므로 사용 가능
        holder.setText(text)
    }

    override fun getItemCount(): Int {
        return textList.size
    }
}


class Holder(val binding: ItemViewpagerBinding): RecyclerView.ViewHolder(binding.root){

    fun setText(text:String){
        binding.textView.text = text
    }
}









