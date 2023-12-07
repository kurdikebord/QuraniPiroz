package com.goran.quran.karim.dang.widgets.checkbox

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.widget.CompoundButton
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatCheckBox
import com.goran.quran.karim.dang.R
import com.goran.quran.karim.dang.utils.extensions.drawable
import com.goran.quran.karim.dang.widgets.compound.PeaceCompoundButton

class PeaceCheckBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : PeaceCompoundButton(context, attrs, defStyleAttr) {
    private var hasInitialButtonDrawable = false
    private var checkBoxButtonCompat: Drawable? = null
    private lateinit var checkBox: CheckBoxHelper

    init {
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.PeaceCompoundButton,
            defStyleAttr,
            0
        )

        if (a.hasValue(R.styleable.PeaceCompoundButton_peaceComp_buttonCompat)) {
            hasInitialButtonDrawable = true
            checkBoxButtonCompat = a.getDrawable(
                R.styleable.PeaceCompoundButton_peaceComp_buttonCompat
            )
        }

        a.recycle()
    }

    override fun initThis() {
        super.initThis()
        super.setOnClickListener { toggle() }
    }

    override fun makeComponents() {
        makeCheckBox()
        super.makeComponents()
    }

    private fun makeCheckBox() {
        checkBox = CheckBoxHelper(context).apply {
            setOnCheckedChangeListener(this@PeaceCheckBox)
            isChecked = initialChecked
        }
        if (hasInitialButtonDrawable) {
            setButtonDrawable(checkBoxButtonCompat)
        }
    }

    override fun getCompoundButton(): CompoundButton {
        return checkBox
    }

    fun setButtonDrawable(buttonDrawable: Drawable?) {
        checkBox.buttonDrawable = buttonDrawable
    }

    fun setButtonDrawable(@DrawableRes resId: Int) {
        setButtonDrawable(context.drawable(resId))
    }

    override fun setOnClickListener(l: OnClickListener?) {
        throw IllegalStateException("Use setOnCheckedChangedListener instead.")
    }

    internal class CheckBoxHelper(context: Context) : AppCompatCheckBox(
        ContextThemeWrapper(context, R.style.PeaceCheckBox),
        null,
        0
    ) {
        init {
            setPaddingRelative(0, 0, 0, 0)
        }
    }
}
