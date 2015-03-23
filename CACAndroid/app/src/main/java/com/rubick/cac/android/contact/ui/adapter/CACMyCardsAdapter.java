package com.rubick.cac.android.contact.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rubick.cac.android.R;
import com.rubick.cac.android.contact.model.CACContactModel;
import com.rubick.cac.android.contact.ui.widget.CACCardContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import androidrubick.utils.Objects;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/3/22 0022.
 */
public class CACMyCardsAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected ViewPager mViewPager;
    protected Stack<View> mRecycledViews = new Stack<View>();
    protected SparseArray<View> mUsedViews = new SparseArray<View>();
    protected List<CACContactModel> mContentData = new ArrayList<CACContactModel>();
    public CACMyCardsAdapter(Context context, ViewPager viewPager) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mViewPager = viewPager;
    }

    @Override
    public void notifyDataSetChanged() {
        //
        transferData();
        super.notifyDataSetChanged();
    }

    protected void transferData() {
        mContentData.add(CACContactModel.simplePhone("印勇1", "15221543209"));
        mContentData.add(CACContactModel.simplePhone("印勇2", "15221543209"));
    }

    @Override
    public int getCount() {
        return mContentData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mInflater.inflate(R.layout.mycards_pager_item, container, false);
        CACMyCardsPagerItemHolder holder = new CACMyCardsPagerItemHolder(view);
        view.setTag(holder);
        resetItemViewState(view);
        container.addView(view);
        mUsedViews.put(position, view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView((View) object);
        resetItemViewState(view);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        animateItemView(mUsedViews.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            resetOther();
        } else if (state == ViewPager.SCROLL_STATE_DRAGGING) {
            resetOther();
        }
    }

    protected void resetOther() {
        final int pos = mViewPager.getCurrentItem();
        for (int i = 0; i < mUsedViews.size(); i++) {
            if (mUsedViews.keyAt(i) == pos) {
                continue;
            }
            resetItemViewState(mUsedViews.valueAt(i));
        }
    }

    protected void resetItemViewState(View view) {
        CACMyCardsPagerItemHolder holder = Objects.getAs(view.getTag());
        holder.cardContainer.resetValues();
    }

    protected void animateItemView(View view) {
        CACMyCardsPagerItemHolder holder = Objects.getAs(view.getTag());
        holder.cardContainer.dump();
    }

    public static class CACMyCardsPagerItemHolder {
        @InjectView(R.id.mycards_pager_item_container)
        public CACCardContainer cardContainer;

        public CACMyCardsPagerItemHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
