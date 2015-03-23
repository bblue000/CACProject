package com.rubick.cac.android.contact.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.nineoldandroids.view.ViewPropertyAnimator;
import com.rubick.cac.android.R;
import com.rubick.cac.android.contact.ui.widget.CACCardContainer;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @InjectView(R.id.btn)
    Button btn;
    @InjectView(R.id.container)
    CACCardContainer container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ButterKnife.inject(this);
        container.dump();
    }

    @OnClick(R.id.btn)
    void onBtnClicked() {
//        if (CACContactManager.getInstance(this).addContact(CACContactModel.simplePhoneWithCom(
//                "YY", "15151877621", "唯品会", "开发工程师"))) {
//            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
//        }

        startActivity(new Intent(this, CACMyCardsActivity.class));

//        container.dump();
    }

    boolean flag;
    @OnClick(R.id.container)
    void onContainerClicked() {
        ViewPropertyAnimator
                .animate(container)
                .setStartDelay(0L)
                .rotationXBy(flag ? -10 : 10)
                .setDuration(100L)
                .setListener(null)
                .start();
        flag = !flag;
    }


}
