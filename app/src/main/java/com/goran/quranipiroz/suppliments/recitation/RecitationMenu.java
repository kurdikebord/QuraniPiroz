package com.goran.quranipiroz.suppliments.recitation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.TypefaceSpan;
import android.view.View;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import androidx.core.content.ContextCompat;
import static com.goran.quranipiroz.utils.univ.RelativePopupWindow.HorizontalPosition.ALIGN_RIGHT;
import static com.goran.quranipiroz.utils.univ.RelativePopupWindow.VerticalPosition.ABOVE;
import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import com.peacedesign.android.utils.Dimen;
import com.peacedesign.android.utils.DrawableUtils;
import com.peacedesign.android.utils.WindowUtils;
import com.goran.quranipiroz.R;
import com.goran.quranipiroz.activities.readerSettings.ActivitySettings;
import com.goran.quranipiroz.databinding.LytRecitationMenuBinding;
import com.goran.quranipiroz.utils.reader.recitation.RecitationManager;
import com.goran.quranipiroz.utils.extensions.ViewKt;
import com.goran.quranipiroz.utils.reader.recitation.RecitationUtils;
import com.goran.quranipiroz.utils.sharedPrefs.SPReader;
import com.goran.quranipiroz.utils.univ.PopupWindow2;
import com.goran.quranipiroz.views.reader.RecitationPlayer;

import kotlin.Unit;

public class RecitationMenu {
    private final RecitationPlayer mPlayer;
    private final PopupWindow2 mPopup;
    private LytRecitationMenuBinding mBinding;

    public RecitationMenu(RecitationPlayer player) {
        mPlayer = player;
        mPopup = new PopupWindow2();
        init();
    }

    private void init() {
        setupPopup();

        AsyncLayoutInflater inflater = new AsyncLayoutInflater(mPlayer.getContext());
        inflater.inflate(R.layout.lyt_recitation_menu, null, (view, resid, parent) -> {
            mBinding = LytRecitationMenuBinding.bind(view);
            mPopup.setContentView(mBinding.getRoot());
            setupView(mBinding);
        });
    }

    private void setupPopup() {
        mPopup.setWidth(Dimen.dp2px(mPlayer.getContext(), 220));
        mPopup.setHeight(WRAP_CONTENT);
        mPopup.setFocusable(true);
        mPopup.setElevation(Dimen.dp2px(getContext(), 3));

        int bgColor = ContextCompat.getColor(getContext(), R.color.colorBGRecitationMenu);
        mPopup.setBackgroundDrawable(DrawableUtils.createBackground(bgColor, Dimen.dp2px(getContext(), 10)));
    }

    private void setupView(LytRecitationMenuBinding binding) {
        binding.repeat.setOnClickListener(v -> binding.repeatCheckbox.toggle());
        binding.autoplay.setOnClickListener(v -> binding.autoplayCheckbox.toggle());
        binding.selectReciter.setOnClickListener(v -> {
            close();
            mPlayer.mActivity.mBinding.readerHeader.openReaderSetting(ActivitySettings.SETTINGS_RECITER);
        });

        int resId = WindowUtils.isRTL(
            mPlayer.getContext()) ? R.drawable.dr_icon_chevron_left : R.drawable.dr_icon_chevron_right;
        Drawable chevronRight = mPlayer.mActivity.drawable(resId);
        binding.selectReciter.setDrawables(mPlayer.mActivity.drawable(R.drawable.dr_icon_recitation), null,
            chevronRight, null);

        binding.repeatCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mPlayer.setRepeat(isChecked);
            ViewKt.disableView(binding.autoplay, isChecked);
        });
        binding.autoplayCheckbox.setOnCheckedChangeListener(
            (buttonView, isChecked) -> mPlayer.setContinueChapter(isChecked));
    }

    private void reSetup(LytRecitationMenuBinding binding) {
        final boolean repeatEnabled = SPReader.getRecitationRepeatVerse(getContext());
        binding.repeatCheckbox.setChecked(repeatEnabled);
        binding.autoplayCheckbox.setChecked(SPReader.getRecitationContinueChapter(getContext()));

        ViewKt.disableView(binding.autoplay, repeatEnabled);

        binding.selectReciter.setText(prepareRecitationTitle(null));
        RecitationManager.prepare(getContext(), false, () -> {
            String subtitle = RecitationUtils.getReciterName(SPReader.getSavedRecitationSlug(getContext()));
            binding.selectReciter.setText(prepareRecitationTitle(subtitle));

            return Unit.INSTANCE;
        });
    }

    private CharSequence prepareRecitationTitle(String subtitle) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        String title = getContext().getString(R.string.strTitleSelectReciter);
        ssb.append(title);

        if (!TextUtils.isEmpty(subtitle)) {
            SpannableString spannable = new SpannableString(subtitle);
            spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.colorText2)), 0,
                subtitle.length(),
                SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new RelativeSizeSpan(0.93f), 0, subtitle.length(),
                SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new TypefaceSpan("sans-serif"), 0, subtitle.length(),
                SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.append("\n").append(spannable);
        }

        return ssb;
    }


    public void open(View anchorView) {
        if (anchorView.getWindowToken() != null) {
            if (mBinding != null) {
                reSetup(mBinding);
            }
            mPopup.showOnAnchor(anchorView, ABOVE, ALIGN_RIGHT);
        }
    }

    public void close() {
        mPopup.dismiss();
    }

    public final Context getContext() {
        return mPlayer.getContext();
    }
}
