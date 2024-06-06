package kr.dori.android.cameraandgallery

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kr.dori.android.cameraandgallery.databinding.ActivityMainBinding
import java.io.File
//오류 1: classpath오류 -> build.gradle에서 해당 버전을 확인할 수 없을 때 발생 -> version 정보를 잘 맞추자. (내 경우에는 fragment.ktx의 버전이 잘못됨)
//오류 2: photoUri 오류 -> 26행 부분이 원래는 var photoUri:Uri? = null 형태였는데 camera.launch(photoUri) 부분에서 처리하지 못했다. launch 부분에는 null값이 들어가면 안 되기 때문에 null safety 사용보다는 그냥 지연초기화 하는게 맞는 듯 하다. (엘비스 오퍼레이션을 쓸 수도 있다.) https://stackoverflow.com/questions/44595529/smart-cast-to-type-is-impossible-because-variable-is-a-mutable-property-tha 이거 참고 ㄱㄱ
//오류 3: 카메라 사용시 이미 카메라 앱이 켜져 있지 않은 상태라면 이동이 안 된다.

class MainActivity : AppCompatActivity() {
    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    lateinit var cameraPermission:ActivityResultLauncher<String> // 카메라 권한
    lateinit var storagePermission:ActivityResultLauncher<String> // 저장소 권한
    lateinit var cameraLauncher:ActivityResultLauncher<Uri> //카메라 앱 호출
    lateinit var galleryLauncher: ActivityResultLauncher<String> //갤러리 앱 호출

    lateinit var photoUri:Uri //TakePicture의 결괏값이 Boolean타입이기 때문에 실제 사진 정보를 얻기 위한 프로퍼티를 따로 지정해야 한다.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(binding.root)

        storagePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){isGranted-> //1번
            if(isGranted){
                setViews() //저장소 권한 요청이 승인되면 setViews()매소드로 화면을 시작한다.
            }else{
                Toast.makeText(baseContext,"외부 저장소 권한을 승인해야 앱을 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
                    finish()
            }
        }
        cameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){ //3번
            if(it){
                openCamera()
            }else{
                Toast.makeText(baseContext,"카메라 권한을 승인해야 카메라를 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()){isSuccess-> //5번
            if(isSuccess){
                binding.imagePreview.setImageURI(photoUri)
            }
        }
        galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()){  uri->
            binding.imagePreview.setImageURI(uri)
        }

        storagePermission.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) //0번
    }


    fun setViews(){ //외부 저장소 권한이 승인되었을 때 호출 -> 버튼 클릭시 카메라 권한 요청 //2번
        binding.buttonCamera.setOnClickListener{
            cameraPermission.launch(android.Manifest.permission.CAMERA)
        }
        binding.buttonGallery.setOnClickListener{
            openGallery()
        }
    }

    fun openCamera(){ //4번
        val photoFile = File.createTempFile( //사진 촬영 후 저장할 임시 파일
            "IMG_",
            ".jpg",
            getExternalFilesDir(Environment.DIRECTORY_PICTURES) //사진 촬영 후 저장할 임시 파일
        )
        photoUri = FileProvider.getUriForFile(
            this,
            "${packageName}.provider",
            photoFile
        )

        cameraLauncher.launch(photoUri)
    }

    fun openGallery(){
        galleryLauncher.launch("image/*")
    }
}
