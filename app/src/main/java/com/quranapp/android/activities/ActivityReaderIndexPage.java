package com.goran.quranipiroz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;
import com.goran.quranipiroz.R;
import com.goran.quranipiroz.activities.base.BaseActivity;
import com.goran.quranipiroz.adapters.utility.ViewPagerAdapter2;
import com.goran.quranipiroz.databinding.ActivityReaderIndexPageBinding;
import com.goran.quranipiroz.databinding.LytReaderIndexHeaderBinding;
import com.goran.quranipiroz.databinding.LytReaderIndexTabBinding;
import com.goran.quranipiroz.frags.readerindex.FragReaderIndexChapters;
import com.goran.quranipiroz.frags.readerindex.FragReaderIndexJuz;
import com.goran.quranipiroz.interfaceUtils.readerIndex.FragReaderIndexCallback;
import com.goran.quranipiroz.utils.simplified.SimpleTabSelectorListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityReaderIndexPage extends BaseActivity {
    private final List<FragReaderIndexCallback> mFragCallbacks = new ArrayList<>();
    private ActivityReaderIndexPageBinding mBinding;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_reader_index_page;
    }

    @Override
    protected void onActivityInflated(@NonNull View activityView, @Nullable Bundle savedInstanceState) {
        mBinding = ActivityReaderIndexPageBinding.bind(activityView);
        activityView.post(this::init);
    }

    private void init() {
        initHeader();
        initViewPager();
        initTabs();
        initSort();
    }

    private void initHeader() {
        LytReaderIndexHeaderBinding header = mBinding.header;
        header.back.setOnClickListener(v -> finish());
        header.search.setOnClickListener(v -> startActivity(new Intent(this, ActivitySearch.class)));
    }

    private void initViewPager() {
        ViewPagerAdapter2 adapter = new ViewPagerAdapter2(this);
        adapter.addFragment(FragReaderIndexChapters.newInstance(), getString(R.string.strTitleReaderChapters));
        adapter.addFragment(FragReaderIndexJuz.newInstance(), getString(R.string.strTitleReaderJuz));
        mBinding.viewPager.setAdapter(adapter);
        mBinding.viewPager.setOffscreenPageLimit(adapter.getItemCount());
        mBinding.viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    private void initTabs() {
        mBinding.header.readerTabLayout.setTabSetupCallback((viewPager, tab, position) -> {
            LytReaderIndexTabBinding binding = LytReaderIndexTabBinding.inflate(LayoutInflater.from(this));
            tab.setCustomView(binding.getRoot());
            ViewPagerAdapter2 adapter = (ViewPagerAdapter2) viewPager.getAdapter();
            if (adapter != null) {
                binding.tabTitle.setText((adapter).getPageTitle(position));
            }
        });
        mBinding.header.readerTabLayout.populateFromViewPager(mBinding.viewPager);

        mBinding.header.readerTabLayout.addOnTabSelectedListener(new SimpleTabSelectorListener() {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                scrollToTop();
            }
        });
    }

    private void initSort() {
        mBinding.header.sort.setVisibility(View.VISIBLE);
        mBinding.header.sort.setOnClickListener(v -> sort());
    }

    private void scrollToTop() {
        mFragCallbacks.get(mBinding.viewPager.getCurrentItem()).scrollToTop(true);
    }

    private void sort() {
        mFragCallbacks.get(mBinding.viewPager.getCurrentItem()).sort(this);
    }

    public void addToCallbacks(FragReaderIndexCallback callback) {
        mFragCallbacks.add(callback);
    }
}