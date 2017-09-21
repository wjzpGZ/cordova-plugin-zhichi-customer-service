package com.wjzp.cordova.sobot;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.sobot.chat.SobotApi;
import com.sobot.chat.api.enumtype.SobotChatTitleDisplayMode;
import com.sobot.chat.api.model.ConsultingContent;
import com.sobot.chat.api.model.Information;
import com.sobot.chat.listener.HyperlinkListener;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 智齿插件.
 */
public class SobotPlugin extends CordovaPlugin {
    private static final String TAG = "SobotPlugin";
    private static final String QIYU_APP_KEY = "SOBOT_APP_KEY";
    private Information information;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.d(TAG, "execute: " + action + "  args " + args.toString());
        if (action.equals("open")) {
            try {
                openServiceActivity(args, callbackContext);
            } catch (JSONException e) {
                e.printStackTrace();
                callbackContext.error("解析错误！");
            }
            return true;
        } else if (action.equals("setUserInfo")) {
            setUserInfo(args, callbackContext);
            return true;
        } else if (action.equals("logout")) {
            //Unicorn.logout();
            SobotApi.exitSobotChat(cordova.getActivity());
            updateUserInfo(new JSONObject());
            SobotPreferences.saveString(SobotPreferences.KEY_USER_INFO,"{}");
                 //Unicorn.updateOptions(options());
            return true;
        }
        return false;
    }

    /**
     * 设置用户信息
     *
     * @param args
     * @param callbackContext
     */
    private void setUserInfo(JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if (args.length() <= 0) {
            callbackContext.error("参数不能为空");
            return;
        }
        JSONObject object = args.getJSONObject(0);
        updateUserInfo(object);

        //  info.setCustomInfo(customInfo);

    }

    private void updateUserInfo(JSONObject object) {

//        { //设置用户信息
////partnerId:'', //用户对接ID，用户唯一标示，通常是企业自有用户系统中的userId
//            tel: '', //电话或手机
//                    email: '', //邮箱
//                uname: '', //昵称
//                visitTitle: '', //对话页标题，若不传入系统将获取用户打开咨询组件时所在页面的标题
//                visitUrl: '', //对话页URL，若不传入系统将获取用户打开咨询组件时所在页面的URL
//                face: '', //头像URL
//                realname: '', //真实姓名
//                weibo: '', //微博账号
//                weixin: '', //微信账号
//                qq: '', //QQ号
//                sex: '', //0 女 1 男
//                birthday: '', //生日，如“1990-01-01”
//                remark: '', //用户的备注信息
//                params: '{"等级":"VIP用户","客户状态":"意向客户"}' //自定义用户信息字段
//        }
        information.setTel(object.optString("tel"));
        information.setRealname(object.optString("realname"));
        information.setEmail(object.optString("email"));
        information.setUname(object.optString("uname"));
        information.setUid(object.optString("partnerId"));
        information.setFace(object.optString("face"));
        information.setWeibo(object.optString("weibo"));
        information.setWeixin(object.optString("weixin"));
        information.setQq(object.optString("qq"));
        information.setBirthday(object.optString("birthday"));
        information.setRemark("remark");
        information.setCustomInfo(json2Map(object.optJSONObject("params")));
        //information.setCustomInfo(object.);
    }

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        Context context = cordova.getActivity().getApplicationContext();
        SobotPreferences.init(context);
        String appKey = webView.getPreferences().getString(QIYU_APP_KEY, "");
        information = new Information();
        information.setAppkey(appKey);
        String userJson = SobotPreferences.getString(SobotPreferences.KEY_USER_INFO);
        try {
            JSONObject jsonObject = new JSONObject(userJson);
            updateUserInfo(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 设置标题烂颜色
        // information.setColor("#ff0000");
        SobotApi.setChatTitleDisplayMode(context, SobotChatTitleDisplayMode.Default, "");
    }

    private void openServiceActivity(JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (args.length() <= 0) {
            callbackContext.error("参数不能为空");
            return;
        }
        JSONObject object = args.getJSONObject(0);
        String title = object.getString("title");
        information.setVisitUrl(title);
        information.setVisitUrl(object.optString("url"));
        SobotApi.setChatTitleDisplayMode(cordova.getActivity(), SobotChatTitleDisplayMode.ShowFixedText, title);
        information.setCustomerFields(json2Map(object.optJSONObject("customerFields")));
//        title: "",// 聊天页面的title
//                titleMode:0,//设置聊天界面标题显示模式 0 客服昵称 1 显示固定文本 2 显示console设置的企业名称（为1是 title 有用）
//                groupId: "",//指定技能组 可选
//                groupName:"",//预设技能组名称，选填
//                satisfaction :false, //返回时是否弹出满意度评价, 默认开启
//                modeType:2,//客服模式控制 -1不控制 按照服务器后台设置的模式运行 1仅机器人 2仅人工 3机器人优先 4人工优先
//                useVoice:true,//是否使用语音功能 true使用 false不使用
//                artificial:true,//默认false：显示转人工按钮。true：智能转人工

        //默认false：显示转人工按钮。true：智能转人工
        information.setArtificialIntelligence(object.optBoolean("artificial",true));

        //是否使用语音功能 true使用 false不使用
        information.setUseVoice(object.optBoolean("useVoice",true));
        //客服模式控制 -1不控制 按照服务器后台设置的模式运行
        //1仅机器人 2仅人工 3机器人优先 4人工优先
        information.setInitModeType(object.optInt("modeType",2));
        information.setSkillSetId( object.optString("groupId"));
        information.setSkillSetName( object.optString("groupName"));
        information.setShowSatisfaction(object.optBoolean("satisfaction" , false));
        int mIndex = object.optInt("titleMode",1);
        if(mIndex > 2 && mIndex < 0){
            mIndex = 1;
        }
        SobotApi.setChatTitleDisplayMode(cordova.getActivity(), SobotChatTitleDisplayMode.values()[mIndex],object.optString("title"));
        // 设置商品信息
        if (object.has("product")) {

            JSONObject productJson = object.getJSONObject("product");
            ConsultingContent consultingContent = new ConsultingContent();
            //咨询内容标题，必填
            consultingContent.setSobotGoodsTitle(productJson.optString("title"));
            //咨询内容图片，选填 但必须是图片地址
            consultingContent.setSobotGoodsImgUrl(productJson.optString("picture"));
            //咨询来源页，必填
            consultingContent.setSobotGoodsFromUrl(productJson.getString("url"));
            //描述，选填
            consultingContent.setSobotGoodsDescribe(productJson.optString("desc"));
            //标签，选填
            consultingContent.setSobotGoodsLable(productJson.optString("note"));
            //可以设置为null
            information.setConsultingContent(consultingContent);

        }
        SobotApi.startSobotChat(cordova.getActivity(), information);
        callbackContext.success();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
            if (allNetworkInfo != null) {
                for (NetworkInfo networkInfo : allNetworkInfo) {
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public static Map<String, String> json2Map(JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        try {
            Iterator<String> keyIter = jsonObject.keys();
            if(keyIter == null ){
                return null;
            }
            String key;
            Object value;
            Map<String, String> valueMap = new HashMap<String, String>();
            while (keyIter.hasNext()) {
                key = (String) keyIter.next();
                value = jsonObject.get(key);
                valueMap.put(key, value.toString());
            }
            return valueMap;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
