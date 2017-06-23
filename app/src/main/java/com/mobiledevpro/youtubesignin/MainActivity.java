package com.mobiledevpro.youtubesignin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mobiledevpro.youtubeauth.YoutubeAuthActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_token)
    TextView mTvToken;

    private String mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //show token
        if (!TextUtils.isEmpty(mToken)) {
            if (mTvToken != null) {
                mTvToken.setText("Token: \n" + mToken);
                mTvToken.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case YoutubeAuthActivity.REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null && data.getExtras().containsKey(YoutubeAuthActivity.KEY_RESULT_TOKEN)) {
                        mToken = data.getStringExtra(YoutubeAuthActivity.KEY_RESULT_TOKEN);
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String errMessage = data != null ? data.getStringExtra(YoutubeAuthActivity.KEY_RESULT_ERROR) : "Cancelled";
                    Toast.makeText(this, errMessage, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }

    }

    @OnClick(R.id.btn_sign_in)
    void onSignIn(Button btn) {
        Intent intent = new Intent(this, YoutubeAuthActivity.class);
        intent.putExtra(YoutubeAuthActivity.KEY_APP_CLIENT_ID, "1066264343435-tfqle85fjni6po0saj26fdpheufqdrc3.apps.googleusercontent.com");
        intent.putExtra(YoutubeAuthActivity.KEY_APP_THEME_RES_ID, R.style.AppTheme_NoActionBar);
        intent.putExtra(YoutubeAuthActivity.KEY_APPBAR_TITLE_RES_ID, R.string.app_name_youtube_auth);
        intent.putExtra(YoutubeAuthActivity.KEY_APPBAR_HOME_ICON_RES_ID, R.drawable.ic_close_24dp);
        startActivityForResult(intent, YoutubeAuthActivity.REQUEST_CODE);
    }


}
