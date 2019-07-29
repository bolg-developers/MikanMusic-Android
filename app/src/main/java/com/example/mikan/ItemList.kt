package com.example.mikan

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.mikan.await.HttpUtil
import kotlinx.android.synthetic.main.activity_itemlist.*
import kotlinx.android.synthetic.main.list_item.view.*

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject

class ItemList : AppCompatActivity() {

    val itemicon = listOf(
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher_round
    )
    val titletext = listOf(
        "音楽の投稿",
        "プレイリスト"
    )
    //val dbhelper = DBHelper(this)
    //var data:String? = null

    // List生成と初期化
    val itemdata = List(titletext.size) { i -> ItemListData(titletext[i], itemicon[i])}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_itemlist)

        // progressbar設定
        //progressBar = findViewById<ProgressBar>(R.id.progressBar)
        //progressBar.visibility = View.INVISIBLE

       /* var cur = dbhelper.GetAll()
        if (cur.moveToFirst()) {
            do {
                data += "DisplayNameは"
                data += "\t"
                data += cur.getString(2)
                data += "です\n"
                Log.d("hs/db", "データの内容$data")
            } while (cur.moveToNext())
        }
        cur.close()

        dbhelper.closeDB()*/

        val adapter = ItemListAdapter(this, itemdata)
        listView.adapter = adapter

        listView.setOnItemClickListener{ adapterView, view, position, id ->
            val name = view.findViewById<TextView>(R.id.itemtextView).text
            Toast.makeText(this, "clicked: $name", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * ItemListAdapter(context: Context, itemlist: List<ItemListData>) : ArrayAdapter<ItemListData>(context, 0, itemlist)
     * @param context
     * @param itemlist
     * ArrayAdapterを継承している
     * */
    class ItemListAdapter(context: Context, itemlist: List<ItemListData>) : ArrayAdapter<ItemListData>(context, 0, itemlist) {

        private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view = convertView
            var holder: ViewHolder

            if (view == null) {
                view = layoutInflater.inflate(R.layout.list_item, parent, false)
                holder = ViewHolder(
                    view?.itemtextView!!,
                    view.itemlistimage
                )
                view.tag = holder
            } else {
                holder = view.tag as ViewHolder
            }

            val itemlist = getItem(position) as ItemListData
            holder.titleTextView.text = itemlist.title
            holder.itemImgView.setImageBitmap(BitmapFactory.decodeResource(context.resources, itemlist.imageId))

            return view
        }
    }

    /**
     * ItemListData(val titletext: String, val imageId: Int)
     * @param title
     * @param imageId
     * データクラス
     * */
    data class ItemListData(val title: String, val imageId: Int)

    /**
     * ViewHolder(val titleTextView: TextView, val itemImgView: ImageView)
     * @param titleTextView
     * @param itemImgView
     * データを保持しておくデータクラス
     * */
    data class ViewHolder(val titleTextView: TextView, val itemImgView: ImageView)


    /**
     * gfunc
     * 音楽の全ジャンルを取得するタスク
     * */
    fun gfunc() = GlobalScope.launch(Dispatchers.Main){
        val ag = HttpUtil()
        var str:String ? = null

        // ここで非同期
        async (Dispatchers.Default){ ag.genres(R.string.baseurl.toString() + "v1/genres")}.await().let {
            // jsonを格納
            val result = JSONObject(it)
            val genres = result.getJSONArray("genres")

            for (item in 0..(genres.length()-1)) {
                // インデックスで情報を指定する
                val randb = genres.getJSONObject(item)
                str += randb.getString("id") + randb.getString("name") + "\n"
            }
            //progressBar.visibility = View.GONE
        }
    }
}
