package kr.dori.android.containerrecyclerview

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kr.dori.android.containerrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val data: MutableList<Memo> = loadData() //data 타입 지정과 반복문으로 data 생성

        var adapter = CustomAdapter() //어댑터 인스턴스화
        adapter.listData = data //어댑터로 아이템뷰와 데이터 연결하기

        binding.recyclerView.adapter = adapter //view에 접근

        binding.recyclerView.layoutManager = LinearLayoutManager(this) //화면에 나오는 방법 정의하기
        }

    fun loadData(): MutableList<Memo>{ //반복문으로 data값 만들기 -> 함수에는 반환값이 있어야지
        val data = mutableListOf<Memo>() //data를 만드는 과정 (val data = mutableListOf<Memo>()랑 뭔 차이)
                //빈 리스트를 만들때에는 만드시 데이터 타입을 명시해줘야 한다. -> 제네릭
        for (no in 1..100){ //데이터 만드는 과정
            val title = "이것이 안드로이드다 $no"
            val date = System.currentTimeMillis()

            var memo = Memo(no,title,date, image = R.drawable.ic_launcher_background)
            data.add(memo)
        }
        return data
        // Log.d("sex","$data")
    }
}