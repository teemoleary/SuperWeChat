package cn.ucai.superwechat.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.bean.Result;
import cn.ucai.superwechat.bean.Wallet;
import cn.ucai.superwechat.data.NetDao;
import cn.ucai.superwechat.data.OkHttpUtils;
import cn.ucai.superwechat.utils.ResultUtils;

public class ChangeActivity extends BaseActivity {

    @BindView(R.id.left_image)
    ImageView leftImage;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.subtitle)
    TextView subtitle;
    @BindView(R.id.tv_change_tip)
    TextView tvChangeTip;
    @BindView(R.id.tv_change_balance)
    TextView tvChangeBalance;
    @BindView(R.id.target_layout)
    LinearLayout targetLayout;

    private View loadingView;
    int change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        loadingView = LayoutInflater.from(this).inflate(R.layout.rp_loading, targetLayout, false);
        targetLayout.addView(loadingView);
        setChangeText(change);
        NetDao.loadChange(this, EMClient.getInstance().getCurrentUser(), new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                if (s != null) {
                    Result result = ResultUtils.getResultFromJson(s, Wallet.class);
                    if (result != null && result.isRetMsg()) {
                        Wallet wallet = (Wallet) result.getRetData();
                        if (wallet != null) {
                            setChangeText(wallet.getBalance());
                        } else {
                            setChangeText(0);
                        }
                    }
                }
                loadingView.setVisibility(View.GONE);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void setChangeText(int change) {
        tvChangeBalance.setText("￥ "+Double.valueOf(change));
    }

    private void initView() {
        leftImage.setImageResource(R.drawable.rp_back_arrow_yellow);
        title.setText("钱包");
        subtitle.setText("云账号钱包服务");
        tvChangeTip.setText("我的零钱");
        tvChangeBalance.setText("￥ 0.00");
    }

    @OnClick({R.id.left_layout, R.id.tv_change_recharge, R.id.tv_change_withdraw})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.tv_change_recharge:

                break;
            case R.id.tv_change_withdraw:

                break;
        }
    }
}