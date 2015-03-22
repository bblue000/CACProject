package com.rubick.cac.android.contact.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.rubick.cac.android.R;
import com.rubick.cac.android.contact.ui.adapter.CACMyCardsAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/3/22 0022.
 */
public class CACMyCardsActivity extends Activity {

    @InjectView(R.id.mycards_vp)
    protected ViewPager mMyCards_VP;
    protected CACMyCardsAdapter mMyCardsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycards);
        initView();
    }

    protected void initView() {
        ButterKnife.inject(this);

        mMyCardsAdapter = new CACMyCardsAdapter(this);
        mMyCards_VP.setAdapter(mMyCardsAdapter);
    }

    protected void initData() {

    }

    protected void updateDataToUI() {
        mMyCardsAdapter.notifyDataSetChanged();
    }
}
