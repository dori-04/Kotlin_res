package kr.dori.android.permissioin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
//import kr.dori.android.permissioin.Manifest
import kr.dori.android.permissioin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    lateinit var activityResult:ActivityResultLauncher<String> //미리 ResultLauncher를 전역으로 설정해두기 -> 이후에 registerForActivity로부터 contract 파라미터를 전달 받는다.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // enableEdgeToEdge()
        setContentView(binding.root)

        activityResult = registerForActivityResult(ActivityResultContracts.RequestPermission()){isGranted-> //registerActivityResult로 런처를 생성한다.
            if(isGranted){
                startProcess()
            }else{
                finish()
            }
        }

        binding.btnCamera.setOnClickListener{
            activityResult.launch(android.Manifest.permission.CAMERA) // 여기서 런처를 실행하여 위에 작성해둔 코드블록을 실행한다.
        }


    }
    fun startProcess(){
        Toast.makeText(this, "카메라를 실행합니다.",Toast.LENGTH_SHORT).show(
        )
    }
}