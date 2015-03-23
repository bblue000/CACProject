package com.rubick.cac.android.contact.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.rubick.cac.android.R;
import com.rubick.cac.android.contact.ui.adapter.CACMyCardsAdapter;
import com.rubick.cac.android.contact.ui.widget.CACCardContainer;

import org.androidrubick.app.BaseFragment;

import butterknife.InjectView;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/3/22 0022.
 */
public class CACMyCardsV1Fragment extends BaseFragment {

    @InjectView(R.id.mycards_container)
    protected CACCardContainer mMyCardsContainer;

    @Override
    public int provideLayoutResId() {
        return R.layout.activity_mycards_v1;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
    }

    @Override
    public void initListener() {
    }

    @Override
    public void updateDataToUI() {
        mMyCardsContainer.dump();
    }
}
