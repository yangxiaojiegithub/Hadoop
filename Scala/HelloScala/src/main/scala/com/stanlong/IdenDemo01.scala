package com.stanlong

/**
  * @author 矢量
  * @date 2020/7/11-15:38
  */
object IdenDemo01 {
  def main(args: Array[String]): Unit = {
    // 首字符为操作符（比如 + - * /），后续字符也需要跟操作符，至少一个
    val ++ = "hello,world!"
    println(++)

    // 用反引号包括的任意字符串，即使时关键字（39个）也可以[true]
    var `true` = "Hello,scala"
    println("内容=" + `true`)
  }
}
