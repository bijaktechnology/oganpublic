package com.incendiary.ambulanceapp.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.incendiary.ambulanceapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TitleBar extends RelativeLayout {

  @BindView(R.id.titlebar_title) TextView mTxtTitle;
  @BindView(R.id.titlebar_caption) TextView mTxtCaption;
  @BindView(R.id.titlebar_action_button) ImageButton mActionButton;
  @BindView(R.id.titlebar_action_right_button) ImageButton mRightActionButton;
  @BindView(R.id.titlebar_divider) View mTitlebarDivider;

  public TitleBar(Context context) {
    this(context, null);
  }

  public TitleBar(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();

    if (isInEditMode()) {
      return;
    }

    TypedArray typedArray =
        context.getTheme().obtainStyledAttributes(attrs, R.styleable.TitleBar, 0, 0);
    int src = typedArray.getResourceId(R.styleable.TitleBar_src, 0);
    int rightSrc = typedArray.getResourceId(R.styleable.TitleBar_rightSrc, 0);

    if (src != 0) mActionButton.setImageResource(src);
    if (rightSrc != 0) {
      mRightActionButton.setImageResource(rightSrc);
      mRightActionButton.setVisibility(View.VISIBLE);
    }
  }

  private void init() {
    LayoutInflater.from(getContext()).inflate(R.layout.custom_view_title_bar, this, true);
    if (isInEditMode()) {
      return;
    }
    ButterKnife.bind(this);
  }

  public void setTitle(@StringRes int resId) {
    mTxtTitle.setText(resId);
  }

  public void setTitle(String title) {
    mTxtTitle.setText(title);
  }

  public void setTitleTextSize(float size) {
    mTxtTitle.setTextSize(size);
  }

  public void setCaption(String caption) {
    mTxtCaption.setText(caption);
  }

  public void setCaptionVisible(boolean isVisible) {
    mTxtCaption.setVisibility(isVisible ? View.VISIBLE : View.GONE);
  }

  public void setShowDivider(boolean isShow) {
    mTitlebarDivider.setVisibility(isShow ? VISIBLE : GONE);
  }

  public void setOnActionButtonClick(OnClickListener onActionButtonClick) {
    mActionButton.setOnClickListener(onActionButtonClick);
  }

  public void setSecondaryActionClick(OnClickListener secondaryActionClick) {
    mRightActionButton.setOnClickListener(secondaryActionClick);
  }

  public void hideActionButton() {
    hideActionButton(true);
  }

  public void hideActionButton(boolean isHide) {
    mActionButton.setVisibility(isHide ? GONE : VISIBLE);
  }
}
