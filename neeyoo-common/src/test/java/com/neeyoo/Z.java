package com.neeyoo;

/**
 * Created by Tianxb.
 * Created on 2019/9/27.
 * Description:
 * 解析:
 *  1.父类静态代码块(java虚拟机加载类时, 就会执行该块代码，故只执行- -次)
 *  2.子类静态代码块(java虚拟机加载类时， 就会执行该块代码，故只执行- -次)
 *  3.父类属性对象初始化
 *  4.父类普通代码块(每次new，每次执行)
 *  4.父类构造函数(每次new,每次执行)
 *  6.子类属性对象初始化
 *  7.子类普通代码块(每次new,每次执行)
 *  8.子类构造函数(每次new,每次执行)
 */
class X {
    Y y = new Y();

    X() {
        System.out.println("X");
    }
}

class Y {
    Y() {
        System.out.println("Y");
    }
}

public class Z extends X {
    Y y = new Y();

    private Z() {
        System.out.println("Z");
    }

    public static void main(String[] args) {
        new Z();
    }
}