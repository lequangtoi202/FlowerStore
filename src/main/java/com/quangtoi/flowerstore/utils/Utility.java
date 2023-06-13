package com.quangtoi.flowerstore.utils;

import jakarta.servlet.http.HttpServletRequest;

public class Utility {
    public static String getSiteURL(HttpServletRequest request){
        String url = request.getRequestURL().toString();
        return url.replace(request.getServletPath(), "");
    }
}
