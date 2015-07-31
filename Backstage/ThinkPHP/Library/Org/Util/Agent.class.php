<?php
    /**
    +------------------------------------------------------------------------------
    * SMOHANCMS客户端信息
    +------------------------------------------------------------------------------
    * @author Smohan 水墨寒
    * @version Smohan's CMS version 3.12
    * @copyright Copyright (c) 2012 - 2014, AllRight By Smohan, Studio.
    * @website http://www.smohan.net
    * @datetime 2014.02.28
    +------------------------------------------------------------------------------
    */
    namespace Org\Util;
    class Agent{
    /**
    *返回客户端IP地址
    */
    public static function GetIp(){
        if (getenv("HTTP_CLIENT_IP") && strcasecmp(getenv("HTTP_CLIENT_IP"), "unknown")) {
         $ip = getenv("HTTP_CLIENT_IP");
        } else if (getenv("HTTP_X_FORWARDED_FOR") && strcasecmp(getenv("HTTP_X_FORWARDED_FOR"), "unknown")) {
         $ip = getenv("HTTP_X_FORWARDED_FOR");
        } else if (getenv("REMOTE_ADDR") && strcasecmp(getenv("REMOTE_ADDR"), "unknown")) {
         $ip = getenv("REMOTE_ADDR");
        } else if (isset ($_SERVER['REMOTE_ADDR']) && $_SERVER['REMOTE_ADDR'] && strcasecmp($_SERVER['REMOTE_ADDR'], "unknown")) {
         $ip = $_SERVER['REMOTE_ADDR'];
        } else {
         $ip = "unknown";
        }
    return $ip;
    }
    /**
    *返回客户端设备
    */
    public static function GetAgent($ua=false){
        $ua = empty($ua) ? $_SERVER['HTTP_USER_AGENT'] : $ua;
        $iphone = strstr(strtolower($ua), 'mobile');
    $android = strstr(strtolower($ua), 'android');
    $windowsPhone = strstr(strtolower($ua), 'phone');
    $androidTablet = self::AndroidTablet($ua);
    $ipad = strstr(strtolower($ua), 'ipad');
    if($ipad){
    return 'Ipad';
    }
        elseif($androidTablet){
         return "Android Tablet";
        }
        elseif($iphone && !$ipad || $android && !$androidTablet || $windowsPhone){
            if($iphone && !$ipad && !$android && !$androidTablet && !$windowsPhone){
             return "Iphone";
            }elseif($iphone && !$ipad && $android && !$androidTablet && !$windowsPhone){
             return "Android mobile phone";
            }elseif(!$iphone && !$ipad && !$android && !$androidTablet && $windowsPhone){
             return "Windows mobile phone";
            }else{
             return "Mobile phone";
            }
        }
    else{
    return 'Desktop('.self::GetOs($ua).')';
    }
    }
    //安卓设备判断
    public static function AndroidTablet($ua){
         if(!$ua) return false;
         if(strstr(strtolower($ua), 'android') ){
    if(!strstr(strtolower($ua), 'mobile')){
    return true;
    }
    }
    }
    /**
    * 返回客户端操作系统
    */
    public static function GetOs($ua='') {
    $os = "";
        $ua = empty($ua) ? $_SERVER['HTTP_USER_AGENT'] : $ua;
    if (stripos($ua, "Googlebot")) {
    $os = "谷歌蜘蛛";
    }
    elseif(stripos($ua, "Baiduspider") !== false) {
    $os = "百度蜘蛛";
    }
    elseif(stripos($ua, "Yahoo!") !== false) {
    $os = "雅虎蜘蛛";
    }
    elseif(stripos($ua, "bingbot")) {
    $os = "必应蜘蛛";
    }
    elseif(stripos($ua, "YRSpider")) {
    $os = "云壤蜘蛛";
    }
    elseif(stripos($ua, "Yeti") !== false) {
    $os = "Naver蜘蛛";
    }
    elseif(stripos($ua, "Windows NT")) {
    switch (substr($ua, stripos($ua, "Windows NT") + 11, 3)) {
             case 5.0:
                 $os = "Windows 2000";
                 break;
             case 5.1:
                 $os = "Windows XP";
                 break;
             case 5.2:
                 $os = "Windows 2003";
                 break;
             case 6.0:
                 $os = "Windows Vista/2008";
                 break;
             case 6.1:
                 $os = "Windows 7";
                 break;
             case 6.2:
                 $os = "Windows 8";
                 break;
             case 6.3:
             $os = "Windows 8.1";
             break;
             default:
                 $os = "Windows";
             }
             $os .= (stripos($ua,"WOW64")) ? "(X64)" : "(X86)";
    }
    elseif(stripos($ua, "Android")) {
    $os = substr($ua, stripos($ua, "Android"), 11);
    }
    elseif(stripos($ua, "Linux")) {
    if (stripos($ua, "i686")) {
    $os = "Linux X86";
    } else {
    $os = "Linux";
    }
    if (stripos($ua, "X11")) {
    $os.= "(X Window)";
    }
    }
    elseif(stripos($ua, "Macintosh")) {
    $os = "Mac";
    }
    elseif(stripos($ua, "IOS")) {
    $os = "iOS";
    }
    elseif(stripos($ua, "ZTE")) {
    $os = "ZTE";
    }
    elseif(stripos($ua, "Windows 98")) {
    $os = "Windows 98";
    } else {
    $os = "未知系统";
    }
    return $os;
    }
    /**
    * 返回客户端浏览器信息
    */
    public static function GetBrowser($ua='') {
        $ua = empty($ua) ? $_SERVER['HTTP_USER_AGENT'] : $ua;
    if (stripos($ua, "Googlebot")) {
    $browser = "谷歌蜘蛛";
    }
    elseif(stripos($ua, "Baiduspider") !== false) {
    $browser = "百度蜘蛛";
    }
    elseif(stripos($ua, "Yahoo!") !== false) {
    $browser = "雅虎蜘蛛";
    }
    elseif(stripos($ua, "bingbot")) {
    $browser = "必应蜘蛛";
    }
    elseif(stripos($ua, "YRSpider")) {
    $browser = "云壤蜘蛛";
    }
    elseif(stripos($ua, "Yeti") !== false) {
    $browser = "Naver蜘蛛";
    }
    elseif(stripos($ua, "Maxthon")) {
    if (stripos($ua, "AppleWebKit")) {
    $browser = "遨游浏览器(极速模式)";
    }
    elseif(stripos($ua, "Trident")) {
    $browser = "遨游浏览器(兼容模式)";
    }
    elseif(stripos($ua, "MAXTHON 2.0")) {
    $browser = "遨游浏览器2.0";
    }
    }
    elseif(stripos($ua, "Firefox")) {
    $browser = "火狐浏览器";
    }
    elseif(stripos($ua, "Opera") == 0 && stripos($ua, "Presto")) {
    $browser = "Opera";
    }
    elseif(stripos($ua, "BIDUBrowser")) {
    if (stripos($ua, "Trident")) {
    $browser = "百度浏览器(兼容模式)";
    }
    elseif(stripos($ua, "AppleWebKit")) {
    $browser = "百度浏览器(极速模式)";
    }
    }
    elseif(stripos($ua, "Ruibin")) {
    $browser = "瑞影浏览器";
    }
    elseif(stripos($ua, "qihu theworld")) {
    if (stripos($ua, "Trident")) {
    $browser = "世界之窗浏览器";
    }
    elseif(stripos($ua, "AppleWebKit")) {
    $browser = "世界之窗浏览器(极速模式)";
    }
    }
    elseif(stripos($ua, "MetaSr")) {
    if (stripos($ua, "Trident")) {
    $browser = "搜狗高速浏览器(兼容模式)";
    }
    elseif(stripos($ua, "AppleWebKit")) {
    $browser = "搜狗高速浏览器(极速模式)";
    }
    }
    elseif(stripos($ua, "LBBROWSER")) {
    if (stripos($ua, "Trident")) {
    $browser = "猎豹浏览器(兼容模式)";
    }
    elseif(stripos($ua, "AppleWebKit")) {
    $browser = "猎豹浏览器(极速模式)";
    }
    }
    elseif(stripos($ua, "YLMFBR")) {
    $browser = "115浏览器";
    }
    elseif(stripos($ua, "QQBrowser")) {
    if (stripos($ua, "Trident")) {
    $browser = "QQ浏览器(兼容模式)";
    }
    elseif(stripos($ua, "AppleWebKit")) {
    $browser = "QQ浏览器(极速模式)";
    }
    }
    elseif(stripos($ua, "TencentTraveler")) {
    $browser = "腾讯TT浏览器";
    }
    elseif(stripos($ua, "TaoBrowser")) {
    if (stripos($ua, "Trident")) {
    $browser = "淘宝浏览器(兼容模式)";
    }
    elseif(stripos($ua, "AppleWebkit")) {
    $browser = "淘宝浏览器(极速模式)";
    }
    }
    elseif(stripos($ua, "CoolNovo")) {
    $browser = "枫树浏览器";
    }
    elseif(stripos($ua, "SaaYaa")) {
    $browser = "闪游浏览器";
    }
    elseif(stripos($ua, "360SE")) {
    $browser = "360安全浏览器";
    }
    elseif(stripos($ua, "360EE")) {
    if (stripos($ua, "Trident")) {
    $browser = "360极速浏览器(兼容模式)";
    }
    elseif(stripos($ua, "AppleWebkit")) {
    $browser = "360极速浏览器(极速模式)";
    }
    }
    elseif(stripos($ua, "Konqueror")) {
    $browser = "Konqueror";
    }
    elseif(stripos($ua, "Chrome")) {
    $browser = "谷歌浏览器";
    }
    elseif(stripos($ua, "Safari")) {
    $browser = "Safari";
    }
    elseif(stripos($ua, "MSIE")) {
    $ver = explode(";", substr($ua, stripos($ua, "MSIE") + 5, 4));
    $ver = $ver[0];
    $browser = "IE ".$ver;
    }
    elseif(stripos($ua, "UCWEB")) {
    $browser = "UCWEB浏览器";
    }
    elseif(stripos($ua, "WAP")) {
    $browser = "Mobile浏览器";
    } else {
    $browser = $ua;
    }
    if ($browser == '') $browser = $ua;
    return $browser;
    }
 }
?>