# Yt4j单机版

ui部分前端还在开发

<p>
 <img src="https://img.shields.io/badge/Yt4j-1.0.0-success.svg" alt="Build Status">
 <img src="https://img.shields.io/badge/spring%20boot-2.6.X-blue" alt="Coverage Status">
</p>


- 基于spring boot、jwt的RBAC权限管理系统
- 提供对docker支持


## 依赖


依赖 | 版本
---|---
Spring Boot | 2.6.x
Mybatis Plus | 3.5.x
hutool | 5.7.x
knife4j |  3.0.3

## 模块说明

```
yt4j
├─yt4j-commons 公共模块
│  ├─yt4j-core 核心包
│  ├─yt4j-data mybatis-plus设置
│  ├─yt4j-flow 复杂业务流程处理
│  ├─yt4j-job xxl-job
│  ├─yt4j-log 统一日志
│  ├─yt4j-security 安全相关
│  ├─yt4j-swagger 引入了军刀
│  └─yt4j-web web定义相关
├─yt4j-dependencies 依赖管理
├─yt4j-modules 业务模块
│  └─yt4j-sys 基础系统【9100】
```

## 代码生成
感觉easycode作者大佬增加了官网，模板可以永久下载了，再也不用担心丢失之苦

acba9f2c5c244d87e9779722ba7392


## 是否预览
~~~
在 yt4j-data DataAutoConfiguration 中有是否预览的设置，这个设置主要是防止演示环境被人删除数据
在本地开发的话，可以将之除去
//增加预览过滤器，增删改操作直接不成功
interceptor.addInnerInterceptor(new PreviewInterceptor(false));
~~~

## 复杂流程设计器
最近在疯狂的看规则引擎，但是发现还是比较复杂的，而且可能不是自己想要的效果，我其实只是想要一个将业务流程画出来的东西，然后让代码按照流程去走，有些流程节点可以进行复用，所以在网上找了找，找到了一个前端项目，非常的惊喜，因为前端就是我想要的，自己写了一套后台，最后想要以stater的方式引入到项目中来
