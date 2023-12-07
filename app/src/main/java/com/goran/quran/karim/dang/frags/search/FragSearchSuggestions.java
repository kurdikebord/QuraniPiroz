package com.goran.quran.karim.dang.frags.search;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.peacedesign.android.utils.ColorUtils;
import com.goran.quran.karim.dang.R;
import com.goran.quran.karim.dang.activities.ActivitySearch;
import com.goran.quran.karim.dang.adapters.search.ADPSearchSugg;
import com.goran.quran.karim.dang.components.search.SearchResultModelBase;
import com.goran.quran.karim.dang.databinding.FragSearchSuggestionsBinding;
import com.goran.quran.karim.dang.frags.BaseFragment;
import com.goran.quran.karim.dang.interfaceUtils.Destroyable;
import com.goran.quran.karim.dang.utils.univ.MessageUtils;

import java.util.ArrayList;

public class FragSearchSuggestions extends BaseFragment implements Destroyable {
    private boolean firstTime = true;
    private FragSearchSuggestionsBinding mBinding;
    private int spannableTextColor;
    private int spannableBGColor;
    private ADPSearchSugg mSearchSuggAdapter;

    public FragSearchSuggestions() {
    }

    public static FragSearchSuggestions newInstance() {
        return new FragSearchSuggestions();
    }

    @Override
    public void onDestroy() {
        if (mSearchSuggAdapter != null) {
            mSearchSuggAdapter.mSuggModels.clear();
        }
        super.onDestroy();
    }

    @Override
    public void destroy() {
        if (mSearchSuggAdapter != null) {
            mSearchSuggAdapter.mSuggModels.clear();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (firstTime || mBinding == null) {
            mBinding = FragSearchSuggestionsBinding.inflate(inflater, container, false);
        }
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!firstTime) {
            return;
        }

        firstTime = false;

        spannableTextColor = ContextCompat.getColor(mBinding.getRoot().getContext(), R.color.colorPrimary);
        spannableBGColor = ContextCompat.getColor(mBinding.getRoot().getContext(), R.color.colorBGLightGrey);

        init(view.getContext());
    }

    private void init(Context context) {
        initSuggRecycler(context);
    }

    private void initSuggRecycler(Context context) {
        mSearchSuggAdapter = new ADPSearchSugg(context);
        mBinding.suggs.setLayoutManager(new LinearLayoutManager(context));
        mBinding.suggs.setAdapter(mSearchSuggAdapter);
        mBinding.suggs.setItemAnimator(null);
    }

    public void initSuggestion(ActivitySearch activitySearch, String query) throws NumberFormatException {
        mBinding.btnClear.setOnClickListener(v -> MessageUtils.showConfirmationDialog(
            activitySearch,
            R.string.msgClearSearchHistory,
            null,
            R.string.strLabelRemoveAll,
            ColorUtils.DANGER,
            () -> {
                activitySearch.mHistoryDBHelper.clearHistories();
                initSuggestion(activitySearch, query);
            }
        ));

        boolean isEmpty = TextUtils.isEmpty(query);

        mBinding.btnClear.setVisibility(
            isEmpty && activitySearch.mHistoryDBHelper.getHistoriesCount() > 0
                ? View.VISIBLE
                : View.GONE
        );

        prepareNShowSugg(activitySearch, query, isEmpty);
    }

    private void prepareNShowSugg(ActivitySearch activitySearch, String query, boolean isEmpty) {
        ArrayList<SearchResultModelBase> suggModels = new ArrayList<>();

        if (!isEmpty) {
            suggModels.addAll(activitySearch.prepareJumper(
                activitySearch.mQuranMeta,
                query
            ));
        }

        suggModels.addAll(prepareHistories(activitySearch, isEmpty ? null : query));

        mSearchSuggAdapter.setSuggModels(activitySearch, suggModels);
        mBinding.suggs.setAdapter(mSearchSuggAdapter);
    }

    private ArrayList<SearchResultModelBase> prepareHistories(ActivitySearch activitySearch, String query) {
        return activitySearch.mHistoryDBHelper.getHistories(query);
    }
}
