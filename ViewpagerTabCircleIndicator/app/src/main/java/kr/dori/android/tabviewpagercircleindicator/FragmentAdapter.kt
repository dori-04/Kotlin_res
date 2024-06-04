package kr.dori.android.tabviewpagercircleindicator

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    var fragmentList = listOf<Fragment>() //recyclerView와는 다르게 중간에 값이 변하지 않기 때문에 immutable type으로 설정

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment { //페이지가 요청될 때 getItem으로 요청되는 페이지의 position이 넘어온다.
        return fragmentList.get(position)
    }
}