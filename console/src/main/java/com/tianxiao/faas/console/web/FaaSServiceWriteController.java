package com.tianxiao.faas.console.web;

import com.tianxiao.faas.biz.command.FaaSServiceSaveCommand;
import com.tianxiao.faas.biz.service.FaaSServiceCommandService;
import com.tianxiao.faas.common.JSONResult;
import com.tianxiao.faas.common.exception.ParamAccessException;
import com.tianxiao.faas.common.exception.biz.BizException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("api/faas/")
public class FaaSServiceWriteController {
    @Resource
    private FaaSServiceCommandService faaSServiceCommandService;

    @RequestMapping("service/save")
    public JSONResult save(FaaSServiceSaveCommand param) {
        JSONResult result = null;
        try {
            boolean save = faaSServiceCommandService.save(param);
            String tips = save ? "保存成功" : "保存失败";
            result =JSONResult.Builder.builder().data(save).isSuccess(true).message(tips).build();
        } catch (ParamAccessException e) {
            result =JSONResult.Builder.builder().isSuccess(false).message(e.getMessage()).build();
        } catch (BizException e) {
            result =JSONResult.Builder.builder().isSuccess(false).message(e.getMessage()).build();
        }
        return result;
    }

    @RequestMapping("service/publish/offline")
    public JSONResult offlinePublish(@RequestParam("id") Integer id) {
        return null;
    }

    @RequestMapping("service/publish/pre")
    public JSONResult prePublish(@RequestParam("id") Integer id) {
        return null;
    }

    @RequestMapping("service/publish/online")
    public JSONResult onlinePublish(@RequestParam("id") Integer id) {
        return null;
    }

    @RequestMapping("service/publish/rollback")
    public JSONResult rollback(@RequestParam("id") Integer id, @RequestParam("toId") Integer toId) {
        return null;
    }
}
