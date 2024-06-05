package kr.dori.android.practicesqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper(context: Context, name:String, version:Int): SQLiteOpenHelper(context,name,null,version) {

    override fun onCreate(db: SQLiteDatabase?) { //DDL의 Create
        //primary key = 해당 컬럼에 중복 값이 없는 유일한 키 -> INTEGER일 때 자동으로 증가시키는 기능이 있다.
        val create = "create table memo (" +
                "no integer primary key, " +
                "content text, " +
                "datetime integer" +
                ")"
        db?.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insertMemo(memo: Memo){ //no는 Primary key로서 자체적으로 업데이트 되기 때문에 따로 put하지 않는다.
//        val values = ContentValues() //ContentValues()는 코틀린의 Map과 같이 키, 값 형태로 사용된다.
//        values.put("content",memo.content)
//        values.put("datetime",memo.datetime)
//        val wd = writableDatabase
//        wd.insert("memo",null,values) //부모 클래스에 이미 수현이 된 writableDatabase를 사용 -> 테이블 이름과 작성한 값을 적어서 insert 시키기
//        wd.close()

        //아래는 쿼리로 작성한 부분이다. no는 입력하지 않기 때문에 콜롬의 이름과 데이터를 직접 입력해줘야 한다.
        val create = "INSERT INTO memo(content, datetime) VALUES('${memo.content}','${memo.datetime}')"
        val db = writableDatabase
        db?.execSQL(create)
        db.close()
    }

    fun selectMemo(): MutableList<Memo>{
        val list = mutableListOf<Memo>()
        val select = "select * from memo" //메모의 전체 데이터를 조회하는 쿼리
        val rd = readableDatabase //d읽기 전용 데이터베이스
        val cursor = rd.rawQuery(select,null) //전체 데이터를 커서 형태로 전환
        while(cursor.moveToNext()){
            val noIdx = cursor.getColumnIndex("no") //테이블에서 no 컬럼의 순서
            val contentIdx =cursor.getColumnIndex("content")
            val dateIdx = cursor.getColumnIndex("datetime") //index 잘못 입력하면 ㅈ됩니다.

            val no = cursor.getLong(noIdx) //컬럼에서 값을 꺼내기 위해서는 먼저 몇 번쨰 컬럼인지를 컬럼명으로 조회해야 합니다.
            val content = cursor.getString(contentIdx)
            val date = cursor.getLong(dateIdx)

            list.add(Memo(no,content,date)) //리스트에 저장해두기
        }
        cursor.close() //메모리 누수 방지를 위해서 반드시 close로 닫아준다.
        rd.close()


        return list
    }

    fun updateMemo(memo:Memo){
        val values = ContentValues() //Insert와 굉장히 유사하다
        values.put("content",memo.content)
        values.put("datetime",memo.datetime)
        val wd = writableDatabase
        wd.update("memo",values,"no= ${memo.no}",null) //지정한 raw의 값들을 수정하는 거임 -> no column의 값을 통해서 알 수 있다.
        wd.close()
    }

    fun deleteMemo(memo: Memo){
        val delete = "delete from memo where no = ${memo.no}" //위 예시들처럼 메서드를 사용하는 것이 아니라 직접 쿼리 문법을 사용하면 더 세밀하게 작업할 수 있다.
        val db = writableDatabase
        db.execSQL(delete)
        db.close()
    }
}
data class Memo(var no: Long?, var content: String, var datetime: Long)