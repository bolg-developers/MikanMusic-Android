package com.example.mikan.NavigationFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mikan.R
import kotlinx.android.synthetic.*
import java.lang.RuntimeException
import android.widget.TabHost





class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

       val view =  initTabs(inflater,container)

        return  view
    }


    // TabHostの初期設定
    protected fun initTabs(inflater: LayoutInflater, container: ViewGroup?):View?{

       var view = inflater.inflate(R.layout.fragment_home, container, false)

        try {

            val tabHost =view.findViewById(R.id.tabHost) as TabHost
            tabHost.setup()

            var spec: TabHost.TabSpec

            // Tab1
            spec = tabHost.newTabSpec("Tab1")
                .setIndicator("投稿")
                .setContent(R.id.linearLayout);
            tabHost.addTab(spec);

            // Tab2
            spec = tabHost.newTabSpec("Tab2")
                .setIndicator("プレイリスト")
                .setContent(R.id.linearLayout2);
            tabHost.addTab(spec);

            // Tab3
            spec = tabHost.newTabSpec("Tab3")
                .setIndicator("Reservation")
                .setContent(R.id.linearLayout3);
            tabHost.addTab(spec);

            tabHost.setCurrentTab(0)
        }catch (e : IllegalArgumentException){
             e.printStackTrace()
        }catch (e : RuntimeException){
             e.printStackTrace()
        }

        return view
     }

}