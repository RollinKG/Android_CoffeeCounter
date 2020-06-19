package com.teamds.coffeecounter.main

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.teamds.coffeecounter.databinding.FragmentStatBinding
import com.teamds.coffeecounter.stat.StatCoffeePieFragment
import com.teamds.coffeecounter.stat.StatDayPieFragment
import com.teamds.coffeecounter.stat.StatWeeklyFragment

/**
 * A simple [Fragment] subclass.
 */
class StatFragment : Fragment() {

    lateinit var binding: FragmentStatBinding
    lateinit var dwmTextArray : Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentStatBinding.inflate(layoutInflater)

        val adapter = ViewPagerAdapter(this.requireActivity())
        binding.statPager.adapter = adapter

        ///------------------------------------------------------------///
        /*
        binding.statPager.apply {
            clipToPadding = false   // allow full width shown with padding
            clipChildren = false    // allow left/right item is not clipped
            offscreenPageLimit = 2  // make sure left/right item is rendered
        }

        // increase this offset to show more of left/right
        val offsetPx = 15.dpToPx(resources.displayMetrics)
        binding.statPager.setPadding(offsetPx, offsetPx, offsetPx, offsetPx)

        // increase this offset to increase distance between 2 items
        val pageMarginPx = 7.dpToPx(resources.displayMetrics)
        val marginTransformer = MarginPageTransformer(pageMarginPx)
        binding.statPager.setPageTransformer(marginTransformer)

         */
        ///------------------------------------------------------------///


        var fragPosition : Int = 0;
        binding.statDwmButtonLeft.visibility=View.INVISIBLE
        val dwmTextArray = arrayOf("지난 7일간","월간 요일별","월간 종류별")

        val clickListener = View.OnClickListener { view ->
            when(view){
                binding.statDwmButtonLeft ->{
                     if(fragPosition > 0 ){
                         fragPosition --;
                     }

                }
                binding.statDwmButtonRight -> {
                    if(fragPosition < 2){
                        fragPosition ++;
                    }
                }
            }

            binding.statPager.setCurrentItem(fragPosition,true)
        }
        binding.statPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                fragPosition = position
                binding.statDwmText.text = dwmTextArray[fragPosition]
                if(position == 0) binding.statDwmButtonLeft.visibility = View.INVISIBLE
                else binding.statDwmButtonLeft.visibility = View.VISIBLE
                if(position == 2) binding.statDwmButtonRight.visibility = View.INVISIBLE
                else binding.statDwmButtonRight.visibility = View.VISIBLE
            }
        })

        binding.statDwmButtonLeft.setOnClickListener(clickListener)
        binding.statDwmButtonRight.setOnClickListener(clickListener)

        return binding.root
    }

    fun Int.dpToPx(displayMetrics: DisplayMetrics): Int = (this * displayMetrics.density).toInt()

    private inner class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa){
        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> StatWeeklyFragment()
                1 -> StatDayPieFragment()
                2  -> StatCoffeePieFragment()
                else -> StatWeeklyFragment()
            }
        }
        override fun getItemCount(): Int = 3
    }



}