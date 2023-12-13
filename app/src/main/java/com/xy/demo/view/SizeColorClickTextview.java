package com.xy.demo.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.xy.demo.R;

import java.util.ArrayList;
import java.util.List;

public class SizeColorClickTextview extends AppCompatTextView {

    public SizeColorClickTextview(Context context) {
        super(context);
    }

    public SizeColorClickTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setTextSize(float size) {
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
    }


    /**
     * 根据flag设置样式
     *
     * @param activity
     * @param text
     */
    public void setMyText(Activity activity, String text, float textSize, int textColor, int isClick) {
        if (text == null) {
            return;
        }
        if (text.startsWith("###")) {
            text = " " + text;
        }
        if (text.endsWith("###")) {
            text = text + " ";
        }
        String[] strings = text.split("###");
        if (strings.length > 2) {
            int size = (strings.length - 1) / 2;
            List<TextPosition> textPositionList = new ArrayList<>();
            String str = text.replace("###", "");
            boolean flag = false;
            if (str.startsWith(" ")) {
                flag = true;
                str = str.substring(1);
            }
            if (str.endsWith(" ")) {
                str = str.substring(0, str.length() - 1);
            }
            for (int i = 0; i < size; i++) {
                int startP = 0;
                int endP = 0;
                if (i == 0) {
                    startP = flag ? 0 : strings[i].length();
                    endP = startP + strings[i + 1].length();
                } else {
                    startP = textPositionList.get(i - 1).Y + strings[i + 1].length();
                    endP = startP + strings[i + 2].length();
                }
                textPositionList.add(new TextPosition(startP, endP));
            }
            SpannableStringBuilder spannableBuilder = new SpannableStringBuilder(str);
            for (int i = 0; i < size; i++) {
                TextPosition textPosition = textPositionList.get(i);
                if (textSize != 0) {
                    if (textSize != -1) {
                        spannableBuilder.setSpan(new RelativeSizeSpan(textSize), textPosition.X, textPosition.Y, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    } else {
                        spannableBuilder.setSpan(new StyleSpan(Typeface.BOLD), textPosition.X, textPosition.Y, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    }
                }
                if (textColor != 0) {
                    spannableBuilder.setSpan(new ForegroundColorSpan(textColor == -1 ? ContextCompat.getColor(getContext(), R.color.colorPrimary) : textColor), textPosition.X, textPosition.Y, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                if (isClick != 0) {
                    spannableBuilder.setSpan(new PublicCallBackSpan(activity, isClick == 2 ? (i + 1) : isClick, false), textPosition.X, textPosition.Y, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                }
            }
            setMovementMethod(LinkMovementMethod.getInstance());
            setText(spannableBuilder);
        } else {
            setText(text);
        }
    }

    class TextPosition {
        public int X, Y;

        public TextPosition(int x, int y) {
            X = x;
            Y = y;
        }
    }
}
