package kr.dori.android.tabviewpagercircleindicator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayoutMediator
import kr.dori.android.tabviewpagercircleindicator.databinding.ActivityMainBinding

class MainActivity : FragmentActivity() {
    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(binding.root)

        val fragmentList = listOf(FragmentA(),FragmentB(),FragmentC(),FragmentD()) //리사이클러뷰의 데이터 저장 역할

        val adapter = FragmentAdapter(this) //이렇게 해야 잘 맞지 FragmentActivity!!
        adapter.fragmentList = fragmentList

        binding.viewPager.adapter = adapter

        val tabTitles = listOf<String>("A","B","C","D")
        TabLayoutMediator(binding.tabLayout,binding.viewPager){tab, position ->
            tab.text = tabTitles[position] //여기서 position은 viewPager의 포지션이다.
        }.attach()

        binding.indicator.setViewPager(binding.viewPager)

    }
}