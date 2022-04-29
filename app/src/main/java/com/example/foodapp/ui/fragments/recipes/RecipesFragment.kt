package com.example.foodapp.ui.fragments.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.viewmodels.MainViewModel
import com.example.foodapp.R
import com.example.foodapp.adapters.RecipesAdapter
import com.example.foodapp.databinding.FragmentRecipesBinding
import com.example.foodapp.util.Constants.Companion.API_KEY
import com.example.foodapp.util.NetworkResult
import com.example.foodapp.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint

// means  This class will take care of injecting members into the Android class
@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipeViewModel: RecipesViewModel
    private val mAdapter by lazy { RecipesAdapter()}

    private lateinit var binding : FragmentRecipesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java) //reflection
        recipeViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipes, container, false)

        setupRecyclerView()
        requestApiData()

        return binding.root

    }

    private fun requestApiData(){
        mainViewModel.getRecipe(recipeViewModel.applyQuery())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { respones ->
            when (respones) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    respones.data.let {
                        if (it != null) {
                            mAdapter.setData(it)
                        }
                    }

                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), respones.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerview.adapter = mAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    fun showShimmerEffect(){
        binding.shimmerLayout.startShimmer()
    }

    fun hideShimmerEffect(){
        binding.shimmerLayout.stopShimmer()
    }


}