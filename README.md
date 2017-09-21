# wjzp-cordova-sobot 

A cordova plugin, a JS version of Sobot SDK 智齿客服插件


# Install

1. ```cordova plugin add https://github.com/wjzpGZ/cordova-plugin-zhichi-customer-service.git  --variable SOBOT_APP_KEY=YOUR_QIYU_APPID```, or using [plugman](https://npmjs.org/package/plugman), [phonegap](https://npmjs.org/package/phonegap), [ionic](http://ionicframework.com/)
2. ```cordova build ios``` or ```cordova build android```


# Usage

##  设置用户信息
```Javascript
    Sobot.setUserInfo(
            { //设置用户信息
                            partnerId:'123333534', //用户对接ID，用户唯一标示，通常是企业自有用户系统中的userId
                            tel: '15285152801', //电话或手机
                            email: 'wjzp_gz@163.com', //邮箱
                            uname: '土豪', //昵称
                            face: 'http://www.jf258.com/uploads/2015-05-16/082449359.jpg', //头像URL
                            realname: '土豪', //真实姓名
                            weibo: '', //微博账号
                            weixin: '', //微信账号
                            qq: '', //QQ号
                            sex: '', //0 女 1 男
                            birthday: '1992-3-2', //生日，如“1990-01-01”
                            remark: '6666', //用户的备注信息
                            params: '{"等级":"VIP用户","客户状态":"意向客户"}' //自定义用户信息字段
            }
        ,function () {
            alert(" setUser  Success")
        },function (msg) {
            alert(msg);
        });
```
## 注销用户信息
```Javascript
    Sobot.logout();

```

## 打开聊天界面
```Javascript
 Sobot.open(
            {
                     title: "",// 聊天页面的title
                     titleMode:0,//设置聊天界面标题显示模式 0 客服昵称 1 显示固定文本 2 显示console设置的企业名称（为1是 title 有用）
                     groupId: "",//指定技能组 可选
                     groupName:"",//预设技能组名称，选填
                     satisfaction :false, //返回时是否弹出满意度评价, 默认开启
                     modeType:2,//客服模式控制 -1不控制 按照服务器后台设置的模式运行 1仅机器人 2仅人工 3机器人优先 4人工优先
                     useVoice:true,//是否使用语音功能 true使用 false不使用
                     artificial:true,//默认false：显示转人工按钮。true：智能转人工
                     source: //  用户发起咨询的页面信息
                     {
                      uri: "",//咨询页面的uri，可以是url，也可以任意能标识来源的字符串
                      title:"",//咨询页面的title
                      custom:"",//可自定义传入的字符串，比如商品详细信息，用户操作状态等等, 在分配客服时该字段会传递给客服。
                    },
                     product:// 访客发起会话时所带的商品消息信息
                     {
                         title : '标题',
                         desc : '商品描述',
                         picture : '商品图片地址',
                         note : '标签',
                         url : '跳转链接',
                     }
                       },function () {
            console.log( " Success ");
        },function (msg) {
            alert(msg);
        });
```

