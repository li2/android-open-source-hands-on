package me.li2.android.androidtextsample;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by weiyi on 3/28/16.
 */
public class SpannableStringApiActivity extends AppCompatActivity {

    @Bind(R.id.textView) TextView mTextView;
    @Bind(R.id.spannableForegroundColor) TextView mForegroundColorTextView;
    @Bind(R.id.spannableBackgroundColor) TextView mBackgroundColorTextView;
    @Bind(R.id.spannableUnderline) TextView mUnderlineTextView;
    @Bind(R.id.spannableStrikethrough) TextView mStrikethroughTextView;
    @Bind(R.id.spannableStyle) TextView mStyleTextView;
    @Bind(R.id.spannableRelativeSize) TextView mRelativeSizeTextView;
    @Bind(R.id.spannableSuperscript) TextView mSuperscriptTextview;
    @Bind(R.id.spannableSubscript) TextView mSubscriptTextview;
    @Bind(R.id.spannableUrl) TextView mUrlTextView;
    @Bind(R.id.spannableImage) TextView mImageTextView;

    private static final String CONTENT = "time is 52h 1314m, go.";
    private static final int START = 8; // the index of 5
    private static final int END = 17; // the index of ,
    private static final String URL = "http://li2.me";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable_api);
        ButterKnife.bind(this);
        setTitle(R.string.action_title_spannable_string_api);

        // plain text
        mTextView.setText(CONTENT);

        SpannableStringBuilder ssb = new SpannableStringBuilder(CONTENT);

        // foreground color text
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.YELLOW);
        setSpanText(mForegroundColorTextView, foregroundColorSpan);

        // background color text
        BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(Color.YELLOW);
        setSpanText(mBackgroundColorTextView, backgroundColorSpan);

        // under line text
        UnderlineSpan underlineSpan = new UnderlineSpan();
        setSpanText(mUnderlineTextView, underlineSpan);

        // strikethrough text
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        setSpanText(mStrikethroughTextView, strikethroughSpan);

        // Style text
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD_ITALIC);
        setSpanText(mStyleTextView, styleSpan);

        // Relative size text
        RelativeSizeSpan relativeLargerSizeSpan = new RelativeSizeSpan(1.5f);
        RelativeSizeSpan relativeSmallerSizeSpan = new RelativeSizeSpan(0.5f);
        setSpanText(mRelativeSizeTextView, relativeLargerSizeSpan);

        // Superscript text
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        setSpanText(mSuperscriptTextview, superscriptSpan, relativeSmallerSizeSpan);

        // Subscript text
        SubscriptSpan subscriptSpan = new SubscriptSpan();
        setSpanText(mSubscriptTextview, subscriptSpan, relativeSmallerSizeSpan);

        // Url text
        setUrlSpanText(mUrlTextView, URL);

        // Image text
        mImageTextView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Deprecated
                    @Override
                    public void onGlobalLayout() {
                        int height = mImageTextView.getHeight();
                        SpannableStringBuilder ssb =
                                addImageToText(SpannableStringApiActivity.this, R.drawable.ic_time, CONTENT, height);
                        mImageTextView.setText(ssb);
                        // must remove listener
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            mImageTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        } else {
                            mImageTextView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        }
                    }
                });
    }

    private void setSpanText(TextView textView, CharacterStyle span) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(CONTENT);
        ssb.setSpan(span, START, END, 0);
        textView.setText(ssb);
    }

    private void setSpanText(TextView textView, CharacterStyle span1, CharacterStyle span2) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(CONTENT);
        ssb.setSpan(span1, START, END, 0);
        ssb.setSpan(span2, START, END, 0);
        textView.setText(ssb);
    }

    private void setSpanText(TextView textView, List<CharacterStyle> spans) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(CONTENT);
        for (CharacterStyle span : spans) {
            ssb.setSpan(span, START, END, 0);
        }
        textView.setText(ssb);
    }

    private SpannableStringBuilder addImageToText(Context context, int drawableId, String text, int height) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        int width = height * drawable.getIntrinsicWidth() / drawable.getIntrinsicHeight();
        drawable.setBounds(0, 0, width, height);

        SpannableStringBuilder ssb = new SpannableStringBuilder(" " + text);
        ssb.setSpan(new ImageSpan(drawable), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return ssb;
    }

    private void setUrlSpanText(TextView textView, final String url) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(CONTENT);
        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(SpannableStringApiActivity.this, url, Toast.LENGTH_LONG).show();
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
            }
        }, START, END, 0);
        textView.setText(ssb);
        // setting the MovementMethod on the TextView that contains the span,
        // otherwise onClick will not be called.
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
