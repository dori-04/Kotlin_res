package kr.dori.android.coroutine

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.dori.android.coroutine.databinding.ActivityMainBinding
import java.net.URL

//코루틴은 스레드와 마찬가지로스코프 함수로서 정해진 스코프에서만 동작하는데, 이는 크게 글로벌 스코프와 코루틴 스코프로 나뉘게 된다.
//글로벌 스코프는 앱의 생명주기와 함께 움직이며 앱이 실행되는 동안은 별도의 생명 주기 관리가 필요하지 않는다.
//코루틴 스코프는 파일 다운로드와 같이 별도의 관리가 필요한 작업일 때 사용한다.

//class 밖을 Top-level이라고 하고 여기에 적는 함수는 메서드가 아닌 걍 함수이다.
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.run {//스코프 함수인 run을 이용해서 스코프 안에서는 binding을 생략할 수 있다.
            btnDownload.setOnClickListener { //버튼을 누르면 바로 코루틴이 실행된다.
                CoroutineScope(Dispatchers.Main).launch {//Main은 UI 컨트롤과 관련된 디스패처이다.
                    progressBar.visibility = View.VISIBLE
                    val url = editUri.text.toString()
                    //loadImage()를 호출하면서 url을 전달해야 하는데, 서로 다른 디스패처끼리 통신을 하기 위해서는 withContext를 사용해야 한다.
                    val bitmap = withContext(Dispatchers.IO) {//디스패처는 코루틴이 실행될 스레드를 정한다.
                        loadImage(url) //여기서 suspend키워드로 인해서 완료될 때까지 다음으로 넘어가지 않는다.
                    }
                    imagePreview.setImageBitmap(bitmap)
                    progressBar.visibility = View.GONE
                }
            }
        }

    }
}
suspend fun loadImage(imageUrl : String): Bitmap { //suspend 키워드 만으로 코루틴으로 만들 수 있다.
    val url = URL(imageUrl) //URL이 가지고 있는 openStream을 bitmap으로 변환 후 반환한다.
    val stream = url.openStream()
    return BitmapFactory.decodeStream(stream)
}