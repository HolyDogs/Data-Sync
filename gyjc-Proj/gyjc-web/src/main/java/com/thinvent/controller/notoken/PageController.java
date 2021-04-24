package com.thinvent.controller.notoken;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 指标池页面
 *
 * @author xufeng
 * @version 1.0
 * @date 2020/7/20 15:09
 **/
@Controller
public class PageController {

    @RequestMapping("/index")
    public String firstPage() {
        return "index.html";
    }

    @RequestMapping("/指标分类XXX-ZBFLXXX")
    public String returnExcel() {
        return "指标分类XXX-ZBFLXXX.xls";
    }

    @RequestMapping("/指标分类")
    public String returnHandImpExcel() {
        return "指标分类.xls";
    }
}
