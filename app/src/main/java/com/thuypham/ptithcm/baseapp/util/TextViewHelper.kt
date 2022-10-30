package com.thuypham.ptithcm.baseapp.util

import android.content.Context
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.thuypham.ptithcm.baseapp.customview.MySpannable
import com.thuypham.ptithcm.baselib.base.extension.openUrlBrowser
import com.thuypham.ptithcm.baselib.base.extension.setOnSingleClickListener
import java.util.regex.Matcher
import java.util.regex.Pattern

object TextViewHelper {

    fun makeTextViewResizable(
        tv: TextView,
        maxLine: Int,
        expandText: String,
        viewMore: Boolean,
        onItemClick: (() -> Unit?)? = null
    ) {
        if (tv.tag == null) {
            tv.tag = tv.text
        }
        val vto: ViewTreeObserver = tv.viewTreeObserver
        vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val obs: ViewTreeObserver = tv.viewTreeObserver
                obs.removeGlobalOnLayoutListener(this)
                if (maxLine == 0) {
                    val lineEndIndex = tv.layout.getLineEnd(0)
                    val text = tv.text.subSequence(0, lineEndIndex - expandText.length + 1).toString() + " " + expandText
                    tv.text = text
                    tv.movementMethod = LinkMovementMethod.getInstance()
                    tv.setText(
                        addClickablePartTextViewResizable(
                            HtmlCompat.fromHtml(tv.text.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY), tv,
                            maxLine, expandText, viewMore, onItemClick
                        ), TextView.BufferType.SPANNABLE
                    )
                } else if (maxLine > 0 && tv.lineCount >= maxLine) {
                    val lineEndIndex = tv.layout.getLineEnd(maxLine - 1)
                    val text = tv.text.subSequence(0, lineEndIndex - expandText.length + 1).toString() + " " + expandText
                    tv.text = text
                    tv.movementMethod = LinkMovementMethod.getInstance()
                    tv.setText(
                        addClickablePartTextViewResizable(
                            HtmlCompat.fromHtml(tv.text.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY), tv, maxLine, expandText,
                            viewMore, onItemClick
                        ), TextView.BufferType.SPANNABLE
                    )
                } else {
                    val lineEndIndex = tv.layout.getLineEnd(tv.layout.lineCount - 1)
                    val text = tv.text.subSequence(0, lineEndIndex).toString() + " " + expandText
                    tv.text = text
                    tv.movementMethod = LinkMovementMethod.getInstance()
                    tv.setText(
                        addClickablePartTextViewResizable(
                            HtmlCompat.fromHtml(tv.text.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY), tv,
                            lineEndIndex, expandText, viewMore, onItemClick
                        ), TextView.BufferType.SPANNABLE
                    )
                }
            }
        })
    }

    private fun addClickablePartTextViewResizable(
        strSpanned: Spanned, tv: TextView,
        maxLine: Int, spannableText: String, viewMore: Boolean, onItemClick: (() -> Unit?)? = null
    ): SpannableStringBuilder {
        val str: String = strSpanned.toString()
        val ssb = SpannableStringBuilder(strSpanned)
        if (str.contains(spannableText)) {
            ssb.setSpan(object : MySpannable(false) {
                override fun onClick(widget: View) {
                    if (viewMore) {
                        tv.layoutParams = tv.layoutParams
                        tv.setText(tv.tag.toString(), TextView.BufferType.SPANNABLE)
                        tv.invalidate()
                        makeTextViewResizable(tv, -1, " See Less", false, onItemClick)
                    } else {
                        tv.layoutParams = tv.layoutParams
                        tv.setText(tv.tag.toString(), TextView.BufferType.SPANNABLE)
                        tv.invalidate()
                        makeTextViewResizable(tv, maxLine, ".. See More", true, onItemClick)
                    }
                    onItemClick?.invoke()
                }
            }, str.indexOf(spannableText), str.indexOf(spannableText) + spannableText.length, 0)
        }
        return ssb
    }

    private fun makeLinkClickable(strBuilder: SpannableStringBuilder, span: URLSpan?, context: Context) {
        val start = strBuilder.getSpanStart(span)
        val end = strBuilder.getSpanEnd(span)
        val flags = strBuilder.getSpanFlags(span)
        val clickable: ClickableSpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                span?.url?.let { context.openUrlBrowser(it, "") }
            }
        }
        strBuilder.setSpan(clickable, start, end, flags)
        strBuilder.removeSpan(span)
    }

    fun setTextViewHTML(textView: TextView, html: String) {
        val sequence: CharSequence = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
        val strBuilder = SpannableStringBuilder(sequence)
        val urls: Array<URLSpan> = strBuilder.getSpans(0, sequence.length, URLSpan::class.java)
        for (span in urls) {
            makeLinkClickable(strBuilder, span, textView.context)
        }
        textView.text = strBuilder
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

    private val URL_PATTERN: Pattern = Pattern.compile("((https?|ftp)(://[-_.!~*'()a-zA-Z0-9;/?:@&=+$,%#]+))")

    interface OnClickListener {
        fun onLinkClicked(link: String?)
    }

    internal class SensibleUrlSpan(url: String?, pattern: Pattern) : URLSpan(url) {
        /**
         * Pattern to match.
         */
        private val mPattern: Pattern
        fun onClickSpan(widget: View?): Boolean {
            val matched: Boolean = mPattern.matcher(url).matches()
            if (matched) {
                super.onClick(widget!!)
            }
            return matched
        }

        init {
            mPattern = pattern
        }
    }

    internal class SensibleLinkMovementMethod : LinkMovementMethod() {
        var isLinkClicked = false
            private set
        var clickedLink: String? = null
            private set

        override fun onTouchEvent(widget: TextView, buffer: Spannable, event: MotionEvent): Boolean {
            val action: Int = event.action
            if (action == MotionEvent.ACTION_UP) {
                isLinkClicked = false
                clickedLink = null
                var x = event.x.toInt()
                var y = event.y.toInt()
                x -= widget.totalPaddingLeft
                y -= widget.totalPaddingTop
                x += widget.scrollX
                y += widget.scrollY
                val layout: Layout = widget.layout
                val line: Int = layout.getLineForVertical(y)
                val off: Int = layout.getOffsetForHorizontal(line, x.toFloat())
                val link: Array<ClickableSpan> = buffer.getSpans(off, off, ClickableSpan::class.java)
                if (link.isNotEmpty()) {
                    val span = link[0] as SensibleUrlSpan
                    isLinkClicked = span.onClickSpan(widget)
                    clickedLink = span.url
                    return isLinkClicked
                }
            }
            super.onTouchEvent(widget, buffer, event)
            return false
        }
    }

    fun autoLink(view: TextView, listener: OnClickListener?) {
        autoLink(view, listener, null)
    }

    private fun autoLink(view: TextView, listener: OnClickListener?, patternStr: String?) {
        val text = view.text.toString()
        if (TextUtils.isEmpty(text)) {
            return
        }
        val spannable: Spannable = SpannableString(text)
        val pattern = if (patternStr.isNullOrEmpty()) URL_PATTERN else Pattern.compile(patternStr)
        val matcher: Matcher = pattern.matcher(text)

        while (matcher.find()) {
            val urlSpan = SensibleUrlSpan(matcher.group(1), pattern)
            spannable.setSpan(urlSpan, matcher.start(1), matcher.end(1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        view.setText(spannable, TextView.BufferType.SPANNABLE)
        val method = SensibleLinkMovementMethod()
        view.movementMethod = method
        if (listener != null) {
            if (method.isLinkClicked) {
                view.setOnSingleClickListener {
                    listener.onLinkClicked(method.clickedLink)
                }
            }
        }
    }

}