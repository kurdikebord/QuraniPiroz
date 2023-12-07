/*
 * Created by Faisal Khan on (c) 22/8/2021.
 */

package com.goran.quran.karim.dang.adapters.transl;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.goran.quran.karim.dang.components.transls.TranslBaseModel;
import com.goran.quran.karim.dang.components.transls.TranslModel;
import com.goran.quran.karim.dang.components.transls.TranslTitleModel;
import com.goran.quran.karim.dang.databinding.LytSettingsTranslItemBinding;
import com.goran.quran.karim.dang.interfaceUtils.OnTranslSelectionChangeListener;
import com.goran.quran.karim.dang.widgets.checkbox.PeaceCheckBox;

import java.util.List;

public class ADPTransls extends ADPTranslBase<ADPTransls.VHTransl> {
    private final OnTranslSelectionChangeListener<TranslModel> mListener;

    public ADPTransls(Context context, List<TranslBaseModel> models, OnTranslSelectionChangeListener<TranslModel> l) {
        super(context, models);
        mListener = l;
    }


    @NonNull
    @Override
    public VHTransl onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final VHTransl vh;

        if (viewType == 0) {
            vh = new VHTransl(createTitleView(parent.getContext()));
        } else {
            vh = new VHTransl(LytSettingsTranslItemBinding.inflate(mInflater, parent, false));
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VHTransl holder, int position) {
        TranslBaseModel model = mModelsVisible.get(position);

        if (model instanceof TranslTitleModel && holder.itemView instanceof TextView) {
            ((TextView) holder.itemView).setText(((TranslTitleModel) model).getLangName());
        } else if (model instanceof TranslModel && holder.itemView instanceof PeaceCheckBox) {
            holder.bind((TranslModel) model);
        }
    }

    class VHTransl extends RecyclerView.ViewHolder {
        private LytSettingsTranslItemBinding mBinding;

        public VHTransl(@NonNull View itemView) {
            super(itemView);
        }

        public VHTransl(@NonNull LytSettingsTranslItemBinding binding) {
            this(binding.getRoot());
            mBinding = binding;
        }

        public void bind(TranslModel translModel) {
            if (mBinding == null) {
                return;
            }

            mBinding.checkbox.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);

            mBinding.checkbox.setTexts(translModel.getBookInfo().getBookName(),
                translModel.getBookInfo().getAuthorName());
            mBinding.checkbox.setChecked(translModel.isChecked());

            mBinding.checkbox.setBeforeCheckChangeListener((button, newState) -> {
                if (mListener.onSelectionChanged(button.getContext(), translModel, newState)) {
                    translModel.setChecked(newState);
                    return true;
                }

                return false;
            });
        }
    }
}
