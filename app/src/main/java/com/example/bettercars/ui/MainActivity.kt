package com.example.bettercars.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.example.bettercars.R
import com.example.bettercars.ui.adapter.TabsAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        setupTabs()
    }

    fun setupView() {
        tabLayout = findViewById(R.id.tl_tabs)
        viewPager = findViewById(R.id.vp_view_pager)

    }

    fun setupTabs() {
        val tabsAdapter = TabsAdapter(this)
        viewPager.adapter = tabsAdapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            //Executa quando seleciona
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    viewPager.currentItem = it.position
                    Log.d("TAG", "Tab selecionada: ${it.position}")
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.d("TAG", "Tab desselecionada")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.d("TAG", "Tab reselecionada")
            }
        })

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.getTabAt(position)?.select()
            }
        })
    }

}