package com.example.mealapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealapp.R
import com.example.mealapp.adapter.FavoriteAdapter
import com.example.mealapp.databinding.FragmentFavoritesBinding
import com.example.mealapp.utils.isVisible
import com.example.mealapp.utils.setupRecyclerView
import com.example.mealapp.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter

    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            //Load data
            viewModel.getFavoritesFoodList()
            viewModel.foodList.observe(viewLifecycleOwner) {
                if (it.data?.isEmpty() == true) {
                    favEmptyLay.isVisible(true, favoriteList)
                    emptyLay.imgDisconnect.setAnimation(R.raw.empty)
                    emptyLay.imgDisconnect.playAnimation()
                } else {
                    favEmptyLay.isVisible(false, favoriteList)
                    favoriteAdapter.setData(it.data!!)
                    favoriteList.setupRecyclerView(LinearLayoutManager(requireContext()), favoriteAdapter)
                    favoriteAdapter.setOnItemClickListener { food ->
                        val direction = FavoritesFragmentDirections.actionHomeToDetail(food.id)
                        findNavController().navigate(direction)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}