package com.contacts.mazaady.home.home

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.contacts.mazaady.R
import com.contacts.mazaady.core.presentation.base.BaseFragmentBinding
import com.contacts.mazaady.core.presentation.dialog.CloseAppDialog
import com.contacts.mazaady.databinding.FragmentHomeBinding
import com.contacts.mazaady.home.home.adapter.AdapterHomeCategory
import com.contacts.mazaady.home.home.adapter.AdapterHomeSubCategory
import com.contacts.mazaady.home.home.adapter.AdapterLiveVideos
import com.contacts.mazaady.home.home.model.ModelHomeSubCategory
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import java.util.TimerTask

@AndroidEntryPoint
class FragmentHome : BaseFragmentBinding<FragmentHomeBinding>() {

    private val adapterLiveVideos by lazy { AdapterLiveVideos() }

    private val adapterHomeCategory by lazy { AdapterHomeCategory() }

    private lateinit var onBackPressedCallback: OnBackPressedCallback

    private var timer: Timer? = null

    private val DELAY_MS: Long = 2500 // Delay in milliseconds before swiping to the next page
    private val PERIOD_MS: Long = 4500 // Interval in milliseconds between each auto-swipe

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleOnBackPressed()
        setUpLiveVideos()
        setUpCategory()
        setUpSubCategory()

    }

    private fun setUpSubCategory() {
        val list = ArrayList<ModelHomeSubCategory>()
        list.add(ModelHomeSubCategory(R.drawable.base_background,1))
        list.add(ModelHomeSubCategory(R.drawable.base_background,2))
        list.add(ModelHomeSubCategory(R.drawable.base_background,3))
        list.add(ModelHomeSubCategory(R.drawable.base_background,4))
        val adapterHomeSubCategory = AdapterHomeSubCategory(list)

        initViewPagerWithPaddingFromStart(adapterHomeSubCategory)
        binding.viewPagerSlider.adapter = adapterHomeSubCategory
        binding.indicator.setViewPager(binding.viewPagerSlider)

        binding.viewPagerSlider.apply {
            val recyclerView = getChildAt(0) as RecyclerView
            recyclerView.apply {
                setPadding(15, 0, 15, 0)
                clipToPadding = false
            }
        }

        if (timer != null) {
            timer?.cancel()
        }
        startAutoSwiping()
    }

    private fun initViewPagerWithPaddingFromStart(adapterHomeSubCategory: AdapterHomeSubCategory) {

        binding.viewPagerSlider.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                binding.viewPagerSlider.apply {
                    clipToPadding = false   // allow full width shown with padding
                    clipChildren = false    // allow left/right item is not clipped
                    offscreenPageLimit = 2  // make sure left/right item is rendered
                }

                // Check if the current position is the last position
                if (position == adapterHomeSubCategory.itemCount - 1) {
                    // Execute your action when the last position is reached

                    //increase this offset to show more of left/right
                    val offsetPx =
                        resources.getDimension(R.dimen.dp_80).toInt()
                    binding.viewPagerSlider.setPadding(offsetPx, 0, 0, 0)

                    //increase this offset to increase distance between 2 items

                } else {

                    //increase this offset to show more of right/left
                    val offsetPx =
                        resources.getDimension(R.dimen.dp_80).toInt()
                    binding.viewPagerSlider.setPadding(0, 0, offsetPx, 0)


                }

                //increase this offset to increase distance between 2 items
                val pageMarginPx =
                    resources.getDimension(R.dimen.dp_15).toInt()
                val marginTransformer = MarginPageTransformer(pageMarginPx)
                binding.viewPagerSlider.setPageTransformer(marginTransformer)
            }
        })


    }

    private fun setUpLiveVideos() {
        binding.rvVideos.adapter = adapterLiveVideos
    }

    private fun setUpCategory() {
        binding.rvCategory.adapter = adapterHomeCategory
    }

    private fun handleOnBackPressed() {
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                CloseAppDialog(itemSelectedForAction = {
                    requireActivity().finish()
                }).show(parentFragmentManager, "close app")
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )
    }

    private fun startAutoSwiping() {
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                binding.viewPagerSlider.post {
                    binding.viewPagerSlider.currentItem =
                        (binding.viewPagerSlider.currentItem + 1) % binding.viewPagerSlider.adapter?.itemCount!!
                }
            }
        }, DELAY_MS, PERIOD_MS)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

}