package com.rubick.cac.android.contact.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.rubick.cac.android.R;
import com.rubick.cac.android.contact.ui.fragment.CACMyCardsFragment;

import org.androidrubick.app.BaseActivity;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/3/22 0022.
 */
public class CACMyCardsActivity extends BaseActivity {

    @Override
    public int provideLayoutResId() {
        return R.layout.layout_one_fragment;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_one_fragment_container, new CACMyCardsFragment(), "content")
                .commit();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void updateDataToUI() {

    }
}
