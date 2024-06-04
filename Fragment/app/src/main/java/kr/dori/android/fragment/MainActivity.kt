package kr.dori.android.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.dori.android.fragment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    lateinit var listFragment: ListFragment

    override fun onCreate(savedInstanceState: Bundle?) { //UI 데이터 타입이 번들인 것을 확인 가능
        super.onCreate(savedInstanceState)
       // enableEdgeToEdge()
        setContentView(binding.root) //초기 뷰를 세팅하는 방법

        setFragment()

        binding.btnSend.setOnClickListener{
            listFragment.setValue("전달할 값")
        }
    }
    fun setFragment(){ //기본 화면 설정
        listFragment=ListFragment() //-> 밖으로 빼서 전역변수로 사용 + 지연 초기화를 했다면 이후에 값을 초기화 줘야 한다. //생성자를 호출하는 이런 순간에 프래그먼트가 호출되지는 않는다. 그냥 객체화

        var bundle = Bundle()
        bundle.putString("key1","List Fragment")
        bundle.putInt("key2",20240527)
        listFragment.arguments = bundle // 프래그맨트를 시작하면서 데이터를 전달하는 방법

        val transaction = supportFragmentManager.beginTransaction() //transaction 시작 -> frament를 다루는 방법
        transaction.add(R.id.frameLayout, listFragment) // transaction에 listFragment를 담기, 이미 listFragment에 ListFrament()를 객체화 시켜놔서 사용 가능

        transaction.commit() //커밋되는 순간에 프래그먼트가 호출된다.
    }

    fun goDetail(){ //ListFragment위에 DetailFragment를 덮어씌우는 역할을 하게 된다.
        val detailFragment = DetailFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frameLayout, detailFragment)
        transaction.addToBackStack("detail")
        transaction.commit()
    }

    fun goBack(){ //뒤로 갈떄는 맨 위에 있는 스택만 제거하면 되기 때문에 트렌젝션으로 새로운 프래그맨트를 컨트롤 할 필요 없음
        onBackPressed()
    }
}