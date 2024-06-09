package com.internship.expensetracker.presenter.screen.activity.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.internship.expensetracker.data.models.OnboardingItem
import com.internship.expensetracker.databinding.OnboardingViewpagerItemLayoutBinding

class OnboardingAdapter(private val list: MutableList<OnboardingItem>): RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {
    class OnboardingViewHolder(val binding: OnboardingViewpagerItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val binding = OnboardingViewpagerItemLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return OnboardingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.binding.apply {
            tvOnboardingTitle.text = list[position].onboardingTitle
            tvOnboardingDescription.text = list[position].onboardingDescription
            imgViewPager.setImageResource(list[position].img)
        }
    }
}