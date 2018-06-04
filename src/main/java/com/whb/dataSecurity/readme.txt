开放接口/RESTful/Api服务的设计和安全方案详解

总体思路

这个涉及到两个方面问题：
一个是接口访问认证问题,主要解决谁可以使用接口（用户登录验证、来路验证）
一个是数据数据传输安全,主要解决接口数据被监听（HTTPS安全传输、敏感内容加密、数字签名）

用户身份验证：Token与Session
开放接口Api服务其实就是客户端与服务端无状态交互的一种形式，这有点类似REST(Representational State Transfer)风格。
普通网站应用一般使用session进行登录用户信息的存储和验证（有状态），而开放接口服务/REST资源请求则使用Token进行登录用户信息的验证（无状态）。Token更像是一个精简版的session。Session主要用于保持会话信息，会在客户端保存一份cookie来保持用户会话有效性，而Token则只用于登录用户的身份鉴权。所以在移动端使用Token会比使用Session更加简易并且有更高的安全性，同时也更加符合RESTful中无状态的定义。

Token交互流程
客户端通过登录请求提交用户名和密码，服务端验证通过后生成一个Token与该用户进行关联，并将Token返回给客户端。
客户端在接下来的请求中都会携带Token，服务端通过解析Token检查登录状态。
当用户退出登录、其他终端登录同一账号、长时间未进行操作时Token会失效，这时用户需要重新登录。

Token生成原理
服务端生成的Token一般为随机的非重复字符串，根据应用对安全性的不同要求，会将其添加时间戳（通过时间判断Token是否被盗用）或url签名（通过请求地址判断Token是否被盗用）后加密进行传输。一般Token内容包含有：用户名/appid，密码/appsecret, 授权url，用户自定义token(用户自定义签名)，时间戳，有效期时长(秒), 系统签名(sign)等。

Api接口服务调用流程：
1. 首先要获取全局唯一的接口调用凭据(access_token)。该过程务必使用https安全传输协议，否则被拦截监听了，用户名和密码等重要数据就都泄漏了。
具体过程：
a. 客户端向服务端通过https协议发送请求，参数包含用户名、密码、请求类型等
b. 服务端接到请求后，验证用户信息是否正确，如果正确，返回access_token和expires。否则返回errorcode和errmsg。
c. 服务端access_token可以存储在session或者redis等内存数据库中，键名(key)为user_id,键值为access_token。
d. 客户端获得access_token后，保存到file或redis等内存数据库中。不推荐保存到session或数据库中，保存到session数据容易丢失，保存到数据库因为涉及IO读写，性能较低。
2. 通过RESTful风格的资源请求格式调用接口，如：
https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
正常情况下，服务端会返回JSON数据包给调用者，成功则返回包含业务数据内容的JSON数据包，失败则返回包含errcode和errmsg的JSON数据包

对于敏感的api接口，需使用https协议
http叫超文本传输协议，使用TCP端口80，默认情况下数据是明文传送的，数据可以通过抓包工具捕获到，因此在interner上，有些比较重要的站点的http服务器需要使用PKI（公钥基础结构）技术来对数据加密！这也就是https了； 
https叫安全的超文本传输协议，使用TCP端口443，他的数据会用PKI中的公钥进行加密，这样抓包工具捕获到的数据包也没有办法看包中的内容，因为他没有密钥，当然篡改也就没有什么意义了，安全性大大提高，要解密数据的话就要用到PKI中的私钥。所以一些安全性比较高的网站如：网上银行，电子商务网站都需要用https访问！

微信access_token设计的原理解析
1. appid：接口身份证号。
2. appsecret：密码。
3. access_token：公众号的全局唯一接口调用凭据，，公众号调用各接口时都需使用access_token。access_token是加密的字符串，其目的是为了接口安全考虑，不然随便就能调用微信服务器的接口会有很大风险。access_token包含的信息有appid, secret, 用户自定义token，授权url，有效时长等。（登陆后的凭据，证明你已经登陆，相当于你拿着票去看演唱会，说明你已经买票了，才会让你进）。
4. expires_in：access_token过期时间，因为这里是第三方服务器调用，所以微信服务器必须返回告知给第三方服务器过期时间，从而让第三方服务器更好处理。access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。
5. openid：为了识别用户，每个用户针对每个公众号会产生一个安全的OpenID，OpenID是使用用户微信号加密后的结果，每个用户对每个公众号有一个唯一的OpenID，开发者可通过OpenID来获取用户基本信息。
6. unionid：用来区分用户的唯一性，因为只要是同一个微信开放平台帐号下的移动应用、网站应用和公众帐号，用户的UnionID是唯一的。换句话说，同一用户，对同一个微信开放平台帐号下的不同应用，UnionID是相同的。

接口调用请求说明
https请求方式: GET
https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
返回说明
正常情况下，微信会返回下述JSON数据包给公众号：
{"access_token":"ACCESS_TOKEN","expires_in":7200} (access_token的存储至少要保留512个字符空间,expires_in单位是秒，有效期目前为2个小时，即7200秒)
错误时微信会返回错误码等信息，JSON数据包示例如下（该示例为AppID无效错误）:
{"errcode":40013,"errmsg":"invalid appid"}