#### 基于SpringBoot的QQ第三方登陆



首先我们应该查看QQ互联的官方文档

https://connect.qq.com/

阅读完后流程大概为：

1.注册一个网站应用

2.下载官方的sdk包

3.进行springboot构建

#### 第一步：注册网站应用

登陆<https://connect.qq.com/>，注册网站应用接入

![img](file:///C:\Users\Administrator\AppData\Roaming\Tencent\Users\525599675\QQ\WinTemp\RichOle\V0`346W(NVK8EVJ6BHD)9{V.png)

#### 网站地址

需要自己注册一个域名作为网站地址。

#### 回调地址

用户点击QQ登录跳转到QQ登录页面，登录成功后，应该跳转回网站。回调地址即在这里用来指定跳转回网站的URL。回调地址注册的目的是为了保障第三方APPID帐户的安全，以免被其他恶意网站盗用。

填写并不难，比如我的网站地址是：http://banoflife.com

那么回调地址就可以填写成：http://banoflife.com/connect

#### 提供方和网站备案号

这里需要自己的云服务器，可以在阿里云上购买学生机进行相应的注册来获取。



注册成功之后，我们会得到APP ID和APP KEY。

![QQ图片20190426171023](C:\Users\Administrator\Desktop\QQ图片20190426171023.png)

#### 第二步：下载官方SDK

这个需要在腾讯开放 平台下载Java_SDK.

<http://wiki.open.qq.com/wiki/website/SDK%E4%B8%8B%E8%BD%BD>

### 第三步：进行springboot构建

##### 首先建立新的项目，引入 spring data jpa，thymeleaf，web，devtools，mysql支持；最终的依赖文件如下：

```
<``dependencies``>
        ``<``dependency``>
            ``<``groupId``>org.springframework.boot</``groupId``>
            ``<``artifactId``>spring-boot-starter-data-jpa</``artifactId``>
        ``</``dependency``>
        ``<``dependency``>
            ``<``groupId``>org.springframework.boot</``groupId``>
            ``<``artifactId``>spring-boot-starter-thymeleaf</``artifactId``>
        ``</``dependency``>
        ``<``dependency``>
            ``<``groupId``>org.springframework.boot</``groupId``>
            ``<``artifactId``>spring-boot-starter-web</``artifactId``>
        ``</``dependency``>
```

 

```
        ``<``dependency``>
            ``<``groupId``>org.springframework.boot</``groupId``>
            ``<``artifactId``>spring-boot-devtools</``artifactId``>
            ``<``scope``>runtime</``scope``>
        ``</``dependency``>
        ``<``dependency``>
            ``<``groupId``>mysql</``groupId``>
            ``<``artifactId``>mysql-connector-java</``artifactId``>
            ``<``scope``>runtime</``scope``>
        ``</``dependency``>
        ``<``dependency``>
            ``<``groupId``>org.springframework.boot</``groupId``>
            ``<``artifactId``>spring-boot-starter-tomcat</``artifactId``>
            ``<``scope``>provided</``scope``>
        ``</``dependency``>
        ``<``dependency``>
            ``<``groupId``>org.springframework.boot</``groupId``>
            ``<``artifactId``>spring-boot-starter-test</``artifactId``>
            ``<``scope``>test</``scope``>
        ``</``dependency``>
        ``<!-- 连接池 -->
        ``<``dependency``>
            ``<``groupId``>com.alibaba</``groupId``>
            ``<``artifactId``>druid</``artifactId``>
            ``<``version``>1.1.10</``version``>
        ``</``dependency``>
        ``<!-- 第三方QQ登录 -->
        ``<``dependency``>
            ``<``groupId``>com.qq</``groupId``>
            ``<``artifactId``>Sdk4J</``artifactId``>
            ``<``version``>2</``version``>
        ``</``dependency``>
    ``</``dependencies``>
```





##### ![QQ图片20190426173804](C:\Users\Administrator\Desktop\项目\QQ第三方登录\QQ图片20190426173804.png)

从下载的SDK中打开第一个Readme.txt

1.直接引入Sdk4J.jar 包至项目工程内
2.修改qqconnectconfig.properties 文件，在指定修改的地方填写自己app的相关信息和要获取的scope权限（前4行信息）注意不要试图修改api的请求地址，这里之所以暴露出来是为了方便兼容以后的变动。
3.将qqconnectconfig.properties文件放到自己的项目的context ClassLoader的可以加载的目录下，一般放在项目的src目录即可。
4.查看demo程序，结合api doc文档，进行接口的调用。



##### 1.引入jar包

为了方便，我们把Sdk4J.jar放到C:\Users\Administrator目录下；

然后cmd进入命令行 ，执行 mvn install:install-file -DgroupId=com.qq -DartifactId=Sdk4J -Dversion=2 -Dpackaging=jar -Dfile=Sdk4J.jar即可；

执行完毕本地仓库就多了一个jar包![QQ图片20190426185046](C:\Users\Administrator\Desktop\项目\QQ第三方登录\QQ图片20190426185046.png)

然后我们就可以在pom中导入第三方依赖

##### 2.修改qqconnectconfig.properties 文件

![QQ图片20190426185317](C:\Users\Administrator\Desktop\项目\QQ第三方登录\QQ图片20190426185317.png)

将前三个改成自己注册获得的即可

##### 3.将qqconnectconfig.properties文件放到自己的项目

将qqconnectconfig.properties放到resources目录下即可

#### 4.查看demo程序，结合api doc文档，进行接口的调用。

进入sdk4j_demo文件，继续查看Readme.txt，这个文档比较复杂难懂，我直接结合自己里理解以及java1234网站上的讲解说明一下。

1.部署之前请先修改./web/WEB-INF/classes 下的qqconnectconfig.properties 文件里边的appid，appkey，redirectUrl 等信息，填入您自己的app信息。

2.引入index.html（将官方的index.jsp修改了一下），qq登陆图片，这里的bug是IDEA自身的问题，如果文件夹里就一个文件夹，就会变成static.images，因此这个bug文件并无其他作用，只是为了让images在static包下。



![QQ图片20190426191522](C:\Users\Administrator\Desktop\项目\QQ第三方登录\QQ图片20190426191522.png)

![QQ图片20190426191855](C:\Users\Administrator\Desktop\项目\QQ第三方登录\QQ图片20190426191855.png)

3.配置application

yml和properties都可以，我这里用的是yml

```
server:
  port: 80
  servlet:
    context-path: /
  tomcat:
    uri-encoding: utf-8


spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/springtest?serverTimezone=GMT
    username: root
    password: hmz990203
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    thymeleaf:
      cache: false
```

```

```

4.controller编写和ServletInitializer的序列化

##### 首先编写与index.html对应的方法

```java
@RequestMapping("/")
public ModelAndView root(){
    ModelAndView mav=new ModelAndView();
    mav.setViewName("index");
    mav.addObject("title","首页");
    return mav;
}
```

##### 其次根据官方的IndexServlet方法

```java
@RequestMapping("/qqLogin")
public void qqLogin(HttpServletRequest request, HttpServletResponse response)throws Exception{
    response.setContentType("text/html;charset=utf-8");
    try {
        response.sendRedirect(new Oauth().getAuthorizeURL(request));
    } catch (QQConnectException e) {
        e.printStackTrace();
    }
}
```

### 最后根据AfterLoginRedirectServlet编写方法

```java
/**
     * 回调方法
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/connect")
    public void connect(HttpServletRequest request, HttpServletResponse response)throws Exception{

        response.setContentType("text/html; charset=utf-8");

        PrintWriter out = response.getWriter();

        try {
            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);

            String accessToken   = null,
                    openID        = null;
            long tokenExpireIn = 0L;


            if (accessTokenObj.getAccessToken().equals("")) {
//                我们的网站被CSRF攻击了或者用户取消了授权
//                做一些数据统计工作
                System.out.print("没有获取到响应参数");
            } else {
                accessToken = accessTokenObj.getAccessToken();
                tokenExpireIn = accessTokenObj.getExpireIn();

                request.getSession().setAttribute("demo_access_token", accessToken);
                request.getSession().setAttribute("demo_token_expirein", String.valueOf(tokenExpireIn));

                // 利用获取到的accessToken 去获取当前用的openid -------- start
                OpenID openIDObj =  new OpenID(accessToken);
                openID = openIDObj.getUserOpenID();

                out.println("欢迎你，代号为 " + openID + " 的用户!");
                request.getSession().setAttribute("demo_openid", openID);



                UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
                UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
                out.println("<br/>");
                if (userInfoBean.getRet() == 0) {
                    out.println(userInfoBean.getNickname() + "<br/>");
                    out.println(userInfoBean.getGender() + "<br/>");

                    out.println("<image src=" + userInfoBean.getAvatar().getAvatarURL30() + "><br/>");
                    out.println("<image src=" + userInfoBean.getAvatar().getAvatarURL50() + "><br/>");
                    out.println("<image src=" + userInfoBean.getAvatar().getAvatarURL100() + "><br/>");
                } else {
                    out.println("很抱歉，我们没能正确获取到您的信息，原因是： " + userInfoBean.getMsg());
                }



            }
        } catch (QQConnectException e) {
            e.printStackTrace();
        }
    }
```

因为是在本地，回调的功能还没有办法实现。