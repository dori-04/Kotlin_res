package kr.dori.android.containerrecyclerview
// String안에는 값을 하나만 담을 수 있기 때문에 dataclass를 사용하여 여러개의 파라미터를 이용한다. ->아이템 레이아웃이 아니라 데이터 형식이다.
data class Memo(var no:Int, var title:String, var timeStamp: Long, val image: Int)
