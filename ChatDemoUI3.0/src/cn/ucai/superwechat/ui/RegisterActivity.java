/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ucai.superwechat.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.superwechat.I;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.SuperWeChatHelper;
import cn.ucai.superwechat.bean.Result;
import cn.ucai.superwechat.data.NetDao;
import cn.ucai.superwechat.data.OkHttpUtils;
import cn.ucai.superwechat.utils.CommonUtils;
import cn.ucai.superwechat.utils.MD5;
import cn.ucai.superwechat.utils.MFGT;

/**
 * register screen
 */
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.nickName)
    EditText nickName;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.confirm_password)
    EditText confirmPassword;

    String userName;
    String nick;
    String pwd;
    String confirm_pwd;

    ProgressDialog pd;
    RegisterActivity mContext;

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_title)
    TextView txtTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.em_activity_register);
        ButterKnife.bind(this);
        mContext = this;
        initView();
    }

    private void initView() {
        imgBack.setVisibility(View.VISIBLE);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText(R.string.register);
    }

    public void register() {
        userName = username.getText().toString().trim();
        nick = nickName.getText().toString().trim();
        pwd = password.getText().toString().trim();
        confirm_pwd = confirmPassword.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, getResources().getString(R.string.User_name_cannot_be_empty), Toast.LENGTH_SHORT).show();
            username.requestFocus();
            return;
        } else if (!userName.matches("[a-zA-Z]\\w{5,15}")) {
            Toast.makeText(mContext, R.string.illegal_user_name, Toast.LENGTH_SHORT).show();
            username.requestFocus();
            return;
        } else if (nick.isEmpty()) {
            Toast.makeText(mContext, R.string.toast_nick_not_isnull, Toast.LENGTH_SHORT).show();
            nickName.requestFocus();
            return;
        } else if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, getResources().getString(R.string.Password_cannot_be_empty), Toast.LENGTH_SHORT).show();
            password.requestFocus();
            return;
        } else if (TextUtils.isEmpty(confirm_pwd)) {
            Toast.makeText(this, getResources().getString(R.string.Confirm_password_cannot_be_empty), Toast.LENGTH_SHORT).show();
            confirmPassword.requestFocus();
            return;
        } else if (!pwd.equals(confirm_pwd)) {
            Toast.makeText(this, getResources().getString(R.string.Two_input_password), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(pwd)) {
            pd = new ProgressDialog(this);
            pd.setMessage(getResources().getString(R.string.Is_the_registered));
            pd.show();
            registerAppServer();
        }

    }

    private void registerAppServer() {
        NetDao.register(mContext, userName, nick, pwd, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                if (result == null) {
                    pd.dismiss();
                } else {
                    if ( result.isRetMsg()) {
                        registerEMServer();
                    } else {
                        if (result.getRetCode() == I.MSG_REGISTER_USERNAME_EXISTS) {
                            CommonUtils.showMsgShortToast(result.getRetCode());
                            pd.dismiss();
                        } else {
                            unregisterAppServer();
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });


    }

    private void unregisterAppServer() {
        NetDao.unregister(mContext, userName, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                pd.dismiss();
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
            }
        });
    }

    private void registerEMServer() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    // call method in SDK
                    EMClient.getInstance().createAccount(userName, MD5.getMessageDigest(pwd));
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (!RegisterActivity.this.isFinishing())
                                pd.dismiss();
                            // save current user
                            SuperWeChatHelper.getInstance().setCurrentUserName(userName);
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registered_successfully), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                } catch (final HyphenateException e) {
                    unregisterAppServer();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (!RegisterActivity.this.isFinishing())
                                pd.dismiss();
                            int errorCode = e.getErrorCode();
                            if (errorCode == EMError.NETWORK_ERROR) {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_anomalies), Toast.LENGTH_SHORT).show();
                            } else if (errorCode == EMError.USER_ALREADY_EXIST) {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.User_already_exists), Toast.LENGTH_SHORT).show();
                            } else if (errorCode == EMError.USER_AUTHENTICATION_FAILED) {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.registration_failed_without_permission), Toast.LENGTH_SHORT).show();
                            } else if (errorCode == EMError.USER_ILLEGAL_ARGUMENT) {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.illegal_user_name), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registration_failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
    @OnClick(R.id.btn_register)
    public void onClickRegister() {
        register();
    }

    @OnClick(R.id.img_back)
    public void onClickBack() {
        MFGT.finish(this);
    }
}
