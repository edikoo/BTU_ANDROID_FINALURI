package com.example.finalurigamocda.fragments


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.finalurigamocda.R
import com.example.meeqvseproject.adapters.ViewPagerFragmentAdapter


class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var viewPagerFragmentAdapter: ViewPagerFragmentAdapter

    private lateinit var viewPager: ViewPager2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPagerFragmentAdapter = ViewPagerFragmentAdapter(getActivity() as AppCompatActivity)

        viewPager = view.findViewById(R.id.viewPager)

        viewPager.adapter = viewPagerFragmentAdapter

    }

}