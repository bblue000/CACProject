package com.rubick.cac.android.contact.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.rubick.cac.android.R;
import com.rubick.cac.android.contact.ui.adapter.CACMyCardsAdapter;

import org.androidrubick.app.BaseFragment;

import butterknife.InjectView;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/3/22 0022.
 */
public class CACMyCardsFragment extends BaseFragment {

    @InjectView(R.id.mycards_vp)
    protected ViewPager mMyCards_VP;
    protected CACMyCardsAdapter mMyCardsAdapter;

    @Override
    public int provideLayoutResId() {
        return R.layout.activity_mycards;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        mMyCardsAdapter = new CACMyCardsAdapter(getFragmentActivity(), mMyCards_VP);
    }

    @Override
    public void initListener() {
        mMyCards_VP.setOnPageChangeListener(mMyCardsAdapter);
    }

    @Override
    public void updateDataToUI() {
        mMyCards_VP.setAdapter(mMyCardsAdapter);
        mMyCardsAdapter.notifyDataSetChanged();
    }
}
