package com.feng.controller;

import cn.hutool.core.date.DateUtil;
import com.feng.bean.CommonResult;
import com.feng.component.WebSocketComponent;
import com.feng.utils.VHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class F102 {
    private static Map<String,String> userIds = new HashMap<>();
    @GetMapping("/main")
    public String F101(){
        String result = "空";
        try{
            VHelper vh = new VHelper("T/F101/index.html");
            result = vh.render();
        }catch (Exception e){
            log.error("/F101 出现错误,错误信息:{},现在时间:{}",e.getMessage(), DateUtil.now());
        }
        return result;
    }
    @GetMapping("/F102")
    public String F102(){
        String result = "空";
        try{
            VHelper vh = new VHelper("T/F102/index.html");
            result = vh.render();
        }catch (Exception e){
            log.error("/F102 出现错误,错误信息:{},现在时间:{}",e.getMessage(), DateUtil.now());
        }
        return result;
    }
    @PostMapping("/F102/ajax1")
    public CommonResult ajax1(@RequestBody Map<String,Object> params){
        CommonResult result = new CommonResult();
        try{
            if(params != null){
                String userId = params.get("userId").toString().trim();
                userIds.put(userId,userId);
                result.setCode(200);
                result.setMsg("success");
                log.info("/F102/ajax1 执行成功,参数信息:{},现在时间:{}",params,DateUtil.now());
            }
        }catch (Exception e){
            result.setMsg("error");
            result.setCode(403);
            log.error("/F102/ajax1 执行错误,参数信息:{},错误信息:{}",params,e.getMessage());
        }
        return result;
    }
}
