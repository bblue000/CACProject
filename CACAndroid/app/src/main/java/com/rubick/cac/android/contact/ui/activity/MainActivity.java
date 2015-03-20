package com.rubick.cac.android.contact.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.nineoldandroids.view.animation.AnimatorProxy;
import com.rubick.cac.android.R;
import com.rubick.cac.android.contact.CACContactManager;
import com.rubick.cac.android.contact.model.CACContactModel;
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
        ViewPropertyAnimator
                .animate(container)
                .scaleXBy(.25f)
                .scaleYBy(.25f)
                .rotationXBy(-75)
                .setStartDelay(1000L)
                .setDuration(500)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ViewPropertyAnimator
                                .animate(container)
                                .rotationXBy(75)
                                .setDuration(1000)
                                .start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .start();
    }

    @OnClick(R.id.btn)
    void onBtnClicked() {
        if (CACContactManager.getInstance(this).addContact(CACContactModel.simplePhoneWithCom(
                "YY", "15151877621", "唯品会", "开发工程师"))) {
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
        }
    }

}
