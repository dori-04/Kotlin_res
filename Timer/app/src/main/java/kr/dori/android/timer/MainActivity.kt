package kr.dori.android.timer

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kr.dori.android.timer.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    var total = 0
    var started = false
    //익명 클래스 생성 방법이다. 생성한 후 바로 변수에 담는 거임
    //이렇게 익명 클래스를 통해 무명 객체를 만든다면 코드에서 반복적으로 사용되는 객체가 있는 경우 따로 인스턴스화를 시키지 않아도 사용할 수 있어 장점이 있지만, 일회용임 -> 이를 해결하기 위한 방법으로는 CO가 있긴 함
    val handler = object : Handler(Looper.getMainLooper()){  //루퍼는 메인스레드에서 자동으로 하나만 생성되면 계속 핸들러를 요구함
        override fun handleMessage(msg: Message) {
            val minute = String.format("%02d",total/60)
            val second = String.format("%02d",total%60)
            binding.textTimer.text = "$minute:$second"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.btnStart.setOnClickListener{
            started = true
            thread(start=true){ //여기서 start는 앞에서 설정한 변수와는 무관
                while(started){
                    Thread.sleep(1000)
                    if(started){
                        total += 1
                        handler?.sendEmptyMessage(0) //핸들러를 호출하는 곳이 한군데(Main)밖에 없으므로 0을 담아서 호출한다.
                    }
                }
            }
        }

        binding.btnStop.setOnClickListener{
            if(started){
                started = false
                total = 0
                binding.textTimer.text = "00:00"
            }
        }



    }
}