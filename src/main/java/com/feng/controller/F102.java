package com.feng.controller;

import cn.hutool.core.date.DateUtil;
import com.feng.utils.VHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class F102 {
    @GetMapping("/F102")
    public String F101(){
        String result = "空";
        try{
            VHelper vh = new VHelper("T/F102/index.html");
            result = vh.render();
        }catch (Exception e){
            log.error("/F101 出现错误,错误信息:{},现在时间:{}",e.getMessage(), DateUtil.now());
        }
        return result;
    }

}
