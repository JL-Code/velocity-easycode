
# READM

## Velocity 模板引擎介绍

Velocity是一个基于java的模板引擎（template engine）。它允许任何人仅仅简单的使用模板语言（template language）来引用由java代码定义的对象。当Velocity 应用于web开发时，界面设计人员可以和java程序开发人员同步开发一个遵循MVC架构的web站点，也就是说，页面设计人员可以只关注页面的显示效果，而由java程序开发人员关注业务逻辑编码。

## Velocity基本语法

`"#"` 用来标识 `Velocity` 的关键字，包括 `#set、#if 、#else、#end、#foreach、#end、#include、#parse、#macro` 等；

`"$"` 用来标识 `Velocity` 的变量；如：`$i、$msg、$TagUtil.options(...)`等。

`"{}"` 用来明确标识 `Velocity` 变量；比如在页面中，页面中有一个 `$someonename`，此时，`Velocity` 将把 `someonename` 作为变量名，若我们程序是想在 `someone` 这个变量的后面紧接着显示 `name` 字符，则上面的标签应该改成 `${someone}name`。

`"!"` 用来强制把不存在的变量显示为空白。如：当找不到 `username` 的时候，`$username` 返回字符串 `"$username"`，而 `$!username` 返回空字符串 `""`

## 注意事项

1. 不要使用 tab 对齐，应该使用空格对齐，否则生成的代码样式错乱。

## 信息参考

* [Velocity 模板引擎语法-博客园-autrol](https://www.cnblogs.com/yangzhinian/p/4885973.html)
