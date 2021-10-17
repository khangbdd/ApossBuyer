package com.example.aposs_buyer.uicontroler.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aposs_buyer.R
import com.example.aposs_buyer.databinding.FragmentCartBinding
import com.example.aposs_buyer.databinding.FragmentCartSecondBinding
import com.example.aposs_buyer.uicontroler.activity.AboutUsActivity
import com.example.aposs_buyer.uicontroler.adapter.CartAdapter
import com.example.aposs_buyer.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartSecondFragment : Fragment(), CartAdapter.ChangeAmount, CartAdapter.OnChoose {

    private val cartAdapter = CartAdapter(this, this)
    private val viewModel: CartViewModel by activityViewModels()
    private lateinit var binding: FragmentCartSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart_second, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.rcCart.adapter = cartAdapter
        binding.rcCart.layoutManager = LinearLayoutManager(binding.rcCart.context, LinearLayoutManager.VERTICAL, false)
        binding.btnGoToCheckOut.setOnClickListener {
            findNavController().navigate(CartSecondFragmentDirections.actionCartSecondFragmentToCheckOutFragment())
        }
        viewModel.size.observe(viewLifecycleOwner, Observer {
            if (it==0) {
                binding.emptyCart.visibility = View.VISIBLE
                binding.fullfillCart.visibility = View.GONE
            }
            else
            {
                binding.emptyCart.visibility = View.GONE
                binding.fullfillCart.visibility = View.VISIBLE
            }
        })
        viewModel.choseSize.observe(viewLifecycleOwner, Observer {
            binding.btnGoToCheckOut.isEnabled = it != 0
        })

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.rcCart)
        return binding.root
    }
    var simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
        ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.DOWN or ItemTouchHelper.UP
        ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
            //Remove swiped item from list and notify the RecyclerView
            val position = viewHolder.adapterPosition
            viewModel.removeItem(position)
            cartAdapter.notifyItemRemoved(position)
            cartAdapter.notifyItemRangeChanged(position, viewModel.lstCartItem.value!!.size)
            viewModel.reCalculateTotal()
        }
    }

    override fun onChangeAmount() {
        viewModel.reCalculateTotal()
    }

    override fun onChose(position: Int) {
        Log.i("viewModel", viewModel.lstCartItem.value!![position].isChoose.toString())
        viewModel.setNewChose()
        viewModel.reCalculateTotal()
        viewModel.setChoseSize()
        cartAdapter.notifyItemChanged(position)
    }
}