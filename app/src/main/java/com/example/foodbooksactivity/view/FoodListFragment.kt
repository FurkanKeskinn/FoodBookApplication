package com.example.foodbooksactivity.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodbooksactivity.adapter.FoodRecyclerAdapter
import com.example.foodbooksactivity.databinding.FragmentFoodListBinding
import com.example.foodbooksactivity.viewmodel.FoodListViewModel


class FoodListFragment : Fragment() {
    private var _binding: FragmentFoodListBinding? = null
    private val binding get() = _binding

    private lateinit var viewModel : FoodListViewModel
    private val recylerFoodAdapter = FoodRecyclerAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFoodListBinding.inflate(inflater,container, false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FoodListViewModel::class.java)
        viewModel.refreshData()



        binding?.foodListRecycler?.layoutManager = LinearLayoutManager(context)
        binding?.foodListRecycler?.adapter = recylerFoodAdapter

        binding?.swipeRefreshLayout?.setOnRefreshListener {
            binding?.foodErrorMessage?.visibility = View.VISIBLE
            binding?.foodErrorMessage?.visibility = View.GONE
            binding?.foodListRecycler?.visibility = View.GONE
            //viewModel.refreshData() bunun yerine alttakini kullanıyoruz her yenilediğimizde veriyi internetten alıyoruz
            viewModel.refreshFromInternet()
            binding?.swipeRefreshLayout?.isRefreshing = false
        }
        observeLiveData()
    }

    fun observeLiveData(){

        viewModel.foods.observe(viewLifecycleOwner, Observer { foods ->

            foods?.let {

                binding?.foodListRecycler?.visibility = View.VISIBLE
                recylerFoodAdapter.foodListUpdate(foods)
            }

        })

        viewModel.foodErrorMessage.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it){
                    binding?.foodErrorMessage?.visibility = View.VISIBLE
                }else{
                    binding?.foodErrorMessage?.visibility = View.GONE
                }
            }
        })

        viewModel.foodsLoading.observe(viewLifecycleOwner, Observer {  loading ->
            loading?.let {
                if(it){
                    binding?.foodListRecycler?.visibility= View.GONE
                    binding?.foodErrorMessage?.visibility= View.GONE
                    binding?.foodLoading?.visibility= View.VISIBLE

                }else{
                    binding?.foodLoading?.visibility= View.GONE
                }
            }

        })
    }

}