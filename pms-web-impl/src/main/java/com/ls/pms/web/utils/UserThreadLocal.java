package com.ls.pms.web.utils;

/**
 * @author yejd
 * @date 2023-04-20 09:12
 * @description
 */
public class UserThreadLocal {

    private static ThreadLocal<User> USER_LOCAL = ThreadLocal.withInitial(null);

    public static void set(User user) {
        if (user != null) {
            USER_LOCAL.set(user);
        }
    }

    public static User get() {
        return USER_LOCAL.get();
    }

    public static void clear() {
        User user = get();
        if (user != null) {
            USER_LOCAL.remove();
        }
    }

}
