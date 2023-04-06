package com.feng.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import com.feng.bean.CommonResult;
import com.feng.component.WebSocketComponent;
import com.feng.utils.VHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
public class F102 {
    @Autowired
    WebSocketComponent webSocketComponent;
//    private static Map<String,String> userIds = new HashMap<>();
    @GetMapping("/main/{userId}")
    public String F101(@PathVariable String userId){
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
    @GetMapping("/ajax2")
    public CommonResult ajax2(){
        CommonResult result = new CommonResult();
        try{
            result.setData(webSocketComponent.SESSION_POOL.keySet());
            result.setCode(200);
            result.setMsg("success");
            log.info("/ajax2 执行成功,现在时间是:{}",DateUtil.now());
        }catch (Exception e){
            log.error("/ajax2 执行错误,错误信息:{},现在时间是:{}",e.getMessage(),DateUtil.now());
        }
        return result;
    }
    @PostMapping("/ajax3")
    public void ajax3(){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userIds",webSocketComponent.SESSION_POOL.keySet());
            webSocketComponent.sendAllMessage(jsonObject.toJSONString());
            log.info("/ajax3 发送信息:{},现在时间:{}",webSocketComponent.SESSION_POOL.keySet(),DateUtil.now());
        }catch (Exception e){
            log.error("/ajax3 执行错误,错误信息:{},现在时间:{}",e.getMessage(),DateUtil.now());
        }
    }
    @PostMapping("/F102/ajax1")
    public CommonResult ajax1(@RequestBody Map<String,Object> params){
        CommonResult result = new CommonResult();
        try{
            if(params != null){
                String userId = params.get("userId").toString().trim();
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
