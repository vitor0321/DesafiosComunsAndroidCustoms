package com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.text_highlighter

import android.text.Spannable
import android.text.SpannableStringBuilder
import androidx.core.content.ContextCompat
import androidx.core.text.backgroundColor
import androidx.core.text.bold
import androidx.core.text.color
import androidx.core.text.italic
import androidx.core.text.scale
import androidx.core.text.strikeThrough
import androidx.core.text.subscript
import androidx.core.text.superscript
import androidx.core.text.underline
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.FragmentTextHighlighterBinding
import com.example.desafioscomunsandroidcustoms.presentation.ui.fragment.BaseFragment
import com.example.desafioscomunsandroidcustoms.util.getColorResource

class TextHighlighterFragment : BaseFragment<FragmentTextHighlighterBinding>() {


    override fun getViewBinding(): FragmentTextHighlighterBinding =
        FragmentTextHighlighterBinding.inflate(layoutInflater)

    override fun initializeUi() {
        binding.textHighlighter.text = getFormattedText()
    }

    private fun getFormattedText(): Spannable {
        val gold = getColorResource(R.color.gold)
        val green = getColorResource(R.color.green)
        return SpannableStringBuilder()
            .color(green) { append("Green text") }
            .append("\nNormal text")
            .scale(0.5F) { append("\nText at half size") }
            .backgroundColor(gold) { append("\nBackground gold") }
            .bold { underline { italic { append("\nBold underlined and Italic") } } }
            .scale(1.5F) { append("\nText with bigger size.") }
            .strikeThrough { append("\nCortar no meio") }
            .append("\nEquação: x")
            .superscript { append("2") }
            .append(" = y e 10")
            .subscript { append("log(n)") }
            .append("\n\nsão formatações bonitas demais! ;)")
    }
}