<?php
// 开启调试模式 建议开发阶段开启 部署阶段注释或者设为false
header("Content-type:text/html;charset=utf-8");
define('APP_DEBUG',TRUE);

// 定义应用目录
define('APP_NAME','Admin');
define('APP_PATH','./Admin/');
// 引入ThinkPHP入口文件
require './ThinkPHP/ThinkPHP.php';