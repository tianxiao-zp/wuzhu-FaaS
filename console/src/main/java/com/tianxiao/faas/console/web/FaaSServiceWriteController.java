package com.tianxiao.faas.console.web;

import com.tianxiao.faas.api.param.FaaSServiceSaveParam;
import com.tianxiao.faas.common.JSONResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/faas/")
public class FaaSServiceWriteController {

    @RequestMapping("service/save")
    public JSONResult save(FaaSServiceSaveParam param) {
        return null;
    }

    @RequestMapping("service/publish/offline")
    public JSONResult offlinePublish(@RequestParam("id") Long id) {
        return null;
    }

    @RequestMapping("service/publish/pre")
    public JSONResult prePublish(@RequestParam("id") Long id) {
        return null;
    }

    @RequestMapping("service/publish/online")
    public JSONResult onlinePublish(@RequestParam("id") Long id) {
        return null;
    }

    @RequestMapping("service/publish/rollback")
    public JSONResult rollback(@RequestParam("id") Long id, @RequestParam("toId") Long toId) {
        return null;
    }
}
