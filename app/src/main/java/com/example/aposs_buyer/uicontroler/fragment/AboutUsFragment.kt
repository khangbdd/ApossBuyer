package com.example.aposs_buyer.uicontroler.fragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.aposs_buyer.R
import com.example.aposs_buyer.databinding.FragmentAboutUsBinding
import com.example.aposs_buyer.uicontroler.adapter.DetailCategoryAdapter
import com.example.aposs_buyer.uicontroler.adapter.DetailCategoryViewPagerAdapter
import com.example.aposs_buyer.viewmodel.AboutUsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutUsFragment : Fragment(), DetailCategoryAdapter.ClickListener {

    private val viewModel: AboutUsViewModel by viewModels()
    private lateinit var binding: FragmentAboutUsBinding
    private val detailCategoryViewPagerAdapter = DetailCategoryViewPagerAdapter(this)
    private var categoriesLeftToRight: Boolean = true
    private val mHandler: Handler = Handler()
    private val categoriesRunnable: Runnable = Runnable() {
        kotlin.run {
            if (categoriesLeftToRight) {
                binding.imageViewPager.currentItem += 1

            } else {
                binding.imageViewPager.currentItem -= 1
            }

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about_us, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.imageViewPager.adapter = detailCategoryViewPagerAdapter
        binding.indicator.setViewPager(binding.imageViewPager)
        setUpViewPagerCallBack()
        binding.rlBackToBefore.setOnClickListener {
            requireActivity().onBackPressed()
        }
        return binding.root
    }

    fun setUpViewPagerCallBack() {
        binding.imageViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mHandler.removeCallbacks(categoriesRunnable)
                if (binding.imageViewPager.currentItem == viewModel.listImage.value!!.size - 1) {
                    categoriesLeftToRight = false
                } else {
                    if (binding.imageViewPager.currentItem == 0) {
                        categoriesLeftToRight = true
                    }
                }
                mHandler.postDelayed(categoriesRunnable, 4000)
            }
        })
    }

    override fun onClick(id: Long, name: String) {

    }
}