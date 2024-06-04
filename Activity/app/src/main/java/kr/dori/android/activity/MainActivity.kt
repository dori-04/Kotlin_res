package kr.dori.android.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kr.dori.android.activity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)} //바인딩

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(binding.root) // 여기까지는 기본 ui 설정

        val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ //registerForActivityResult로 런처를 생성, 컨트렉트로 StartActivityForResult
            if(it.resultCode == RESULT_OK){ //돌려받은 값이 정상인지 체크하는 코드이다.
                val message = it.data?.getStringExtra("returnValue") //정상이라면 돌려받아서 변수에 저장
                //Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                binding.wow.text = message
            }
        }

        val intent = Intent(this, SubActivity::class.java)
        intent.putExtra("from1","Hello Bundle") //intent에 타겟 엑티비티 말고도 번들을 사용하여 데이터도 담을 수 있다.
        intent.putExtra("from2",2021)

        binding.btnStart.setOnClickListener{activityResult.launch(intent)} //여기서 런처를 실행시켜서 값을 받아올 수 있다.
    }
}
