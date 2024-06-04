package kr.dori.android.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.dori.android.fragment.databinding.FragmentListBinding


class ListFragment : Fragment() {
    var mainActivity: MainActivity? = null

    lateinit var binding: FragmentListBinding //지연 초기화 설정을 했다면 이후에 값을 초기화 시켜주는 코드가 필요하다.
   // val binding by lazy {FragmentListBinding.inflate(layoutInflater)} -> onCreateView의 파라미터에서 inflater를 정의해서 중복으로 안 쓰는 것 같다.
    override fun onCreateView( //onCreateViewHolder처럼 엑티비티가 프래그맨트를 요청하면 매서드를 통해 뷰를 만들어 반환한다.
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater,container,false) //<<이것이 안드로이드다>> 388pg -> 프래그먼트는 바인딩 생성 시에 onCreateView() 매서드 안에서만 사용
       //할 수 있는 파라미터가 필요하기 때문에 전역으로 lateinit으로 선언만 해야 한다. !!!!! 기모찌
      //  return inflater.inflate(R.layout.fragment_list, container, false) -> 생성한 뷰를 바로 리턴하는 방법
        binding.btnNext.setOnClickListener{mainActivity?.goDetail()} //onAttach를 통해 전달받은 mainAcitivity의 메소드 사용
        binding.textTitle.text = arguments?.getString("key1") //onCreate안에 넣음으로서 프래그먼트를 생성하면서 아규먼트로 값을 받아오는 방법이다.
        binding.textValue.text ="${arguments?.getInt("key2")}"
        return binding.root
    }

    override fun onAttach(context: Context) { //파라미터로 컨텍스트 -> mainActivity의 코드를 전달받게 됨
        super.onAttach(context)

        if(context is MainActivity)mainActivity = context //onAttach를 통해 넘어오는 Context에서만 상위 엑티비티를 꺼내서 사용할 수 있다.
    }
    fun setValue(value:String){ //이미 onCreate로 프래그맨트가 생성된 후에 값을 전달받기 위해서는 setValue 매서드를 사용해야 한다.
        binding.textFromActivity.text = value // -> 파라미터값 이용하기
    }
}























