package com.rubick.cac.android.contact.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.rubick.cac.android.R;
import com.rubick.cac.android.contact.CACContactManager;
import com.rubick.cac.android.contact.model.CACContactModel;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @InjectView(R.id.btn)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ButterKnife.inject(this);

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
