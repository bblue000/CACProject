package com.rubick.cac.android.contact.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.rubick.cac.android.contact.model.CACContactModel;
import com.rubick.cac.android.contact.ui.widget.CACCardContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/3/22 0022.
 */
public class CACMyCardsAdapter extends PagerAdapter {

    protected Context mContext;
    protected List<CACContactModel> mContentData = new ArrayList<CACContactModel>();
    public CACMyCardsAdapter(Context context) {
        mContext = context;
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
        CACCardContainer cardContainer = new CACCardContainer(mContext);
        container.addView(cardContainer);
        return container;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
