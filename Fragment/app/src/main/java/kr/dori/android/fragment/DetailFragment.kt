package kr.dori.android.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.dori.android.fragment.databinding.FragmentDetailBinding

class DetailFragment: Fragment(){
    lateinit var mainActivity: MainActivity //ListFragment와 다르게 이렇게 설정하면 null오류를 막을 수 있다.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.btnBack.setOnClickListener{mainActivity.goBack()}
        return binding.root //버튼 이벤트가 포함되어 있는 바인딩을 반환시킨다. -> 메인엑티비티는 onAttach를 사용하여 객체화시켜서 사용할 수 있다.
    } //객체화가 핵심

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as MainActivity //이것도 ListFragment와는 좀 다르게
    }


}