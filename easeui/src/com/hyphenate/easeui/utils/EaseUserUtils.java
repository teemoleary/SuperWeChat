package com.hyphenate.easeui.utils;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.controller.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.domain.Group;
import com.hyphenate.easeui.domain.User;

public class EaseUserUtils {
    
    static EaseUserProfileProvider userProvider;
    
    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }
    
    /**
     * get EaseUser according username
     * @param username
     * @return
     */
    public static EaseUser getUserInfo(String username){
        if (userProvider != null) {
            return userProvider.getUser(username);
        }
        return null;
    }
    /**
     * get User according username
     * @param username
     * @return
     */
    public static User getAppUserInfo(String username){
        if (userProvider != null) {
            return userProvider.getAppUser(username);
        }
        return null;
    }
    public static User getCurrentAppUserInfo(){
        String userName = EMClient.getInstance().getCurrentUser();
        if (userProvider != null) {
            return userProvider.getAppUser(userName);
        }
        return null;
    }

    /**
     * set user avatar
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView){
    	EaseUser user = getUserInfo(username);
        if(user != null && user.getAvatar() != null){
            try {
                int avatarResId = Integer.parseInt(user.getAvatar());
                Glide.with(context).load(avatarResId).into(imageView);
            } catch (Exception e) {
                //use default avatar
                Glide.with(context).load(user.getAvatar()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.default_hd_avatar).into(imageView);
            }
        }else{
            Glide.with(context).load(R.drawable.default_hd_avatar).into(imageView);
        }
    }
    /**
     * set user's nickname
     */
    public static void setUserNick(String username,TextView textView){
        if(textView != null){
        	EaseUser user = getUserInfo(username);
        	if(user != null && user.getNick() != null){
        		textView.setText(user.getNick());
        	}else{
        		textView.setText(username);
        	}
        }
    }
    /**
     * set user avatar
     * @param hxid
     */
    public static void setAppGroupAvatar(Context context, String hxid, ImageView imageView){
        if(hxid!=null){
            try {
                int avatarResId = Integer.parseInt(Group.getAvatar(hxid));
                Glide.with(context).load(avatarResId).into(imageView);
            } catch (Exception e) {
                //use default avatar
                Glide.with(context).load(Group.getAvatar(hxid)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.default_hd_avatar).into(imageView);
            }
        }else{
            Glide.with(context).load(R.drawable.default_hd_avatar).into(imageView);
        }
    }

    /**
     * set user avatar
     * @param username
     */
    public static void setAppUserAvatar(Context context, String username, ImageView imageView){
        User user = getAppUserInfo(username);
        if (user == null) {
            user = new User(username);
        }
        if(user != null && user.getAvatar() != null){
            try {
                int avatarResId = Integer.parseInt(user.getAvatar());
                Glide.with(context).load(avatarResId).into(imageView);
            } catch (Exception e) {
                //use default avatar
                Glide.with(context).load(user.getAvatar()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.default_hd_avatar).into(imageView);
            }
        }else{
            Glide.with(context).load(R.drawable.default_hd_avatar).into(imageView);
        }
    }

    /**
     * set user avatar
     * @param path
     */
    public static void setAppUserPathAvatar(Context context, String path, ImageView imageView){
        if (path != null) {
            try {
                int avatarResId = Integer.parseInt(path);
                Glide.with(context).load(avatarResId).into(imageView);
            } catch (Exception e) {
                //use default avatar
                Glide.with(context).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.default_hd_avatar).into(imageView);
            }
        }else{
            Glide.with(context).load(R.drawable.default_hd_avatar).into(imageView);
        }
    }
    /**
     * set group avatar
     */
    public static void setLiveAvatar(Context context, String hxid, ImageView imageView){
        if (hxid != null) {
            String cover = getLiveAvatar(hxid);
            try {
                int avatarResId = Integer.parseInt(cover);
                Glide.with(context).load(avatarResId).into(imageView);
            } catch (Exception e) {
                //use default avatar
                Glide.with(context).load(cover).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.default_hd_avatar).into(imageView);
            }
        }else{
            Glide.with(context).load(R.drawable.default_hd_avatar).into(imageView);
        }
    }
    static public String getLiveAvatar(String hxid){
        String path = "http://101.251.196.90:8000/SuperWeChatServerV2.0/downloadAvatar?name_or_hxid="+hxid+"&avatarType=chatroom_icon&m_avatar_suffix=.jpg";
        return path;
    }
    /**
     * set user's nickname
     */
    public static void setAppUserNick(String username,TextView textView){
        if(textView != null){
            User user = getAppUserInfo(username);
            if(user != null && user.getMUserNick() != null){
                textView.setText(user.getMUserNick());
            }else{
                textView.setText(username);
            }
        }
    }

    public static void setCurrentAppUserAvatar(FragmentActivity activity, ImageView ivProfileAvatar) {
        String userName = EMClient.getInstance().getCurrentUser();
        setAppUserAvatar(activity, userName, ivProfileAvatar);
    }

    public static void setCurrentAppUserNick(TextView tvProfileNickname) {
        String userName = EMClient.getInstance().getCurrentUser();
        setAppUserNick(userName, tvProfileNickname);
    }


    public static void setCurrentAppUserName(TextView tvProfileUsername) {
        String userName = EMClient.getInstance().getCurrentUser();
        setAppUserName("微信号：", userName, tvProfileUsername);
    }

    public static void setAppUserNameWithNo(String userName, TextView tvProfileUsername) {
        setAppUserName("", userName, tvProfileUsername);
    }

    private static void setAppUserName(String s, String userName, TextView tvProfileUsername) {
        tvProfileUsername.setText(s + userName);
    }

}
