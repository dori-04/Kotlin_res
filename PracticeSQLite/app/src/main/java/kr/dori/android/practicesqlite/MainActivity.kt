package kr.dori.android.practicesqlite

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kr.dori.android.practicesqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    val helper = SqliteHelper(this,"memo",1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val adapter = RecyclerAdapter()

        adapter.helper = helper

        adapter.listData.addAll(helper.selectMemo()) //이렇게 함으로서 앱을 종료했다가 들어가도 최근 목록들이 보이게 됨

        binding.recyclerMemo.adapter =  adapter
        binding.recyclerMemo.layoutManager = LinearLayoutManager(this)

        binding.buttonSave.setOnClickListener{
            if(binding.editMemo.text.toString().isNotEmpty()){
                val memo = Memo(null,binding.editMemo.text.toString(),System.currentTimeMillis())
                helper.insertMemo(memo)

                adapter.listData.clear() //기존에 있던 listdata를 초기화하고
                adapter.listData.addAll(helper.selectMemo()) // 새롭게 목록을 불러와서 어댑터에 세팅하고 갱신해야지 번호가 갱신된다.
                adapter.notifyDataSetChanged()

                binding.editMemo.setText("")

            }
        }
    }
}