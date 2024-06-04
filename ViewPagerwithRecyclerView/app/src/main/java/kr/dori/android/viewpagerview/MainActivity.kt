package kr.dori.android.viewpagerview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.tabs.TabLayoutMediator
import kr.dori.android.viewpagerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(binding.root)

        val textList = listOf("뷰A","뷰B","뷰C","뷰D")
        val customAdapter = CustomPagerAdapter()
        customAdapter.textList = textList

        binding.viewPager.adapter=customAdapter

        val tabtitles = listOf("View A","View B","View C","View D")
        TabLayoutMediator(binding.tabLayout,binding.viewPager){
            tab, position ->
            tab.text = tabtitles[position]
        }.attach()

    }
}