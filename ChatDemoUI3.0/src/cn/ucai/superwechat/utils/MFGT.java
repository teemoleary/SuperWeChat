package cn.ucai.superwechat.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import cn.ucai.superwechat.I;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.ui.AddContactActivity;
import cn.ucai.superwechat.ui.AddFriendActivity;
import cn.ucai.superwechat.ui.ChangeActivity;
import cn.ucai.superwechat.ui.ChatActivity;
import cn.ucai.superwechat.ui.GroupsActivity;
import cn.ucai.superwechat.ui.LiveDetailsActivity;
import cn.ucai.superwechat.ui.LoginActivity;
import cn.ucai.superwechat.ui.MainActivity;
import cn.ucai.superwechat.ui.NewFriendsMsgActivity;
import cn.ucai.superwechat.ui.NewGroupActivity;
import cn.ucai.superwechat.ui.PersonalProfileActivity;
import cn.ucai.superwechat.ui.PublicGroupsActivity;
import cn.ucai.superwechat.ui.RechargeActivity;
import cn.ucai.superwechat.ui.RegisterActivity;
import cn.ucai.superwechat.ui.SettingsActivity;
import cn.ucai.superwechat.ui.StartLiveActivity;
import cn.ucai.superwechat.ui.UserProfileActivity;


public class MFGT {
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
    public static void gotoMainActivity(Activity context){
        startActivity(context, MainActivity.class);
    }
    public static void startActivity(Activity context,Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(context,cls);
        context.startActivity(intent);
    }
    public static void startActivity(Context context,Intent intent){
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
    public static void startActivityForResult(Activity context, Intent intent, int requestCode) {
        context.startActivityForResult(intent, requestCode);
        context.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * 跳转到登录Activity
     * @param context
     */
    public static void gotoLoginActivity(Activity context) {
        startActivity(context, LoginActivity.class);
    }

    /**
     * 跳转到注册Activity
     * @param context
     */
    public static void gotoRegisterActivity(Activity context) {
        startActivity(context, RegisterActivity.class);
    }
    /**
     * 跳转到设置
     * @param context
     */
    public static void gotoSettingActivity(Activity context) {
        startActivity(context, SettingsActivity.class);
    }

    /**
     * 跳转到个人资料Activity
     * @param context
     */
    public static void gotoUserProfileActivity(Activity context) {
        startActivity(context, UserProfileActivity.class);
    }
    /**
     * 跳转到个人资料Activity
     * @param context
     */
    public static void gotoAddContactActivity(Activity context) {
        startActivity(context, AddContactActivity.class);
    }
    public static void gotoPersonalProfileActivity(Activity context,String userName) {
        Intent intent = new Intent();
        intent.setClass(context, PersonalProfileActivity.class);
        intent.putExtra(I.User.USER_NAME, userName);
        startActivity(context, intent);
    }
    public static void gotoAddFriendActivity(Activity context,String  userName) {
        Intent intent = new Intent();
        intent.setClass(context, AddFriendActivity.class);
        intent.putExtra(I.User.USER_NAME, userName);
        startActivity(context, intent);
    }

    public static void gotoNewFriendsMsgActivity(Activity context) {
        startActivity(context, NewFriendsMsgActivity.class);
    }
    public static void gotoChatActivity(Activity context,String  userName) {
        Intent intent = new Intent();
        intent.setClass(context, ChatActivity.class);
        intent.putExtra("userId", userName);
        startActivity(context, intent);
    }

    public static void gotoGroupsActivity(Activity context) {
        startActivity(context, GroupsActivity.class);
    }
    public static void gotoNewGroupActivity(Activity context) {
        startActivity(context, NewGroupActivity.class);
    }
    public static void gotoPublicGroupsActivity(Activity context) {
        startActivity(context, PublicGroupsActivity.class);
    }
    public static void gotoStartLiveActivity(Activity context) {
        startActivity(context, StartLiveActivity.class);
    }
    public static void gotoLiveDetailsActivity(Activity context) {
        startActivity(context, LiveDetailsActivity.class);
    }
    public static void gotoChangeActivity(Activity context) {
        startActivity(context, ChangeActivity.class);
    }
    public static void gotoRechargeActivity(Activity context) {
        startActivity(context, RechargeActivity.class);
    }
}
