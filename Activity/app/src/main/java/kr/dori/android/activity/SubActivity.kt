package kr.dori.android.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kr.dori.android.activity.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity() {

    val binding by lazy { ActivitySubBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //    enableEdgeToEdge()
        setContentView(binding.root)

        binding.to1.text = intent.getStringExtra("from1") //Main -> Sub로 통신하는 방법
        binding.to2.text = "${intent.getIntExtra("from2", 0)}"

        val returnIntent = Intent()
        binding.btnClose.setOnClickListener{
             //반환할 때는 대상을 지정하지 않아도 된다.
            returnIntent.putExtra("returnValue",binding.editMessage.text.toString()) //key와 value 작성, 이걸 밖으로 빼면 앱 실행때의 text값이 입력되므로 null로 반환된다.

            Log.d("xibal","${binding.editMessage.text}")
            setResult(RESULT_OK,returnIntent) //컨텍스트가 제공하는 setResult 메소드, 첫번째 파라미터에는 상태값, 두번째가 인텐트
            finish() //서브엑티비티 종료
        }
    }
}
























