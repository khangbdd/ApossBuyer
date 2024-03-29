package com.example.aposs_buyer.uicontroler.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aposs_buyer.R
import com.example.aposs_buyer.databinding.FragmentNotificationBinding
import com.example.aposs_buyer.uicontroler.activity.CartSecondActivity
import com.example.aposs_buyer.uicontroler.adapter.NotificationAdapter
import com.example.aposs_buyer.viewmodel.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotificationFragment : Fragment(), NotificationAdapter.NotificationInterface {

    private lateinit var binding: FragmentNotificationBinding

    private val viewModel: NotificationViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_notification,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        val notificationAdapter = NotificationAdapter(this)
        binding.notifications.adapter = notificationAdapter
        setBackButton()
        setCartButton()
        return binding.root
    }

    private fun setBackButton() {
        binding.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setCartButton() {
        binding.cart.setOnClickListener {
            startActivity(Intent(this.context, CartSecondActivity::class.java))
        }
    }

    override fun onSeeNowClick(id: Long) {
        findNavController().navigate(NotificationFragmentDirections.actionNotificationFragmentToDetailOrderFragment(id))
    }
}