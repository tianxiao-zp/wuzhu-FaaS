package com.tianxiao.faas.console.web;

import com.tianxiao.faas.biz.command.FaaSServiceSaveCommand;
import com.tianxiao.faas.biz.domain.FaaSServiceDomain;
import com.tianxiao.faas.biz.factory.FaaSServiceDomainFactory;
import com.tianxiao.faas.biz.service.FaaSServiceCommandService;
import com.tianxiao.faas.common.JSONResult;
import com.tianxiao.faas.common.exception.ParamAccessException;
import com.tianxiao.faas.common.exception.biz.BizException;
import com.tianxiao.faas.common.exception.biz.LockedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("api/faas/")
public class FaaSServiceWriteController {
    @Resource
    private FaaSServiceCommandService faaSServiceCommandService;
    @Resource
    private FaaSServiceDomainFactory faaSServiceDomainFactory;


    @RequestMapping("service/save")
    public JSONResult edit(@RequestParam("id")Integer id) {
        FaaSServiceDomain faaSServiceDomain;
        JSONResult result = null;
        try {
            faaSServiceDomain = faaSServiceCommandService.edit(id, "tianxiao");
            result = JSONResult.Builder.builder().isSuccess(true).data(faaSServiceDomainFactory.build(faaSServiceDomain)).build();
        } catch (ParamAccessException e) {
            result = JSONResult.Builder.builder().isSuccess(false).message(e.getMessage()).build();
        } catch (BizException e) {
            result = JSONResult.Builder.builder().isSuccess(false).message(e.getMessage()).build();
        } catch (LockedException e) {
            result = JSONResult.Builder.builder().isSuccess(false).message(e.getMessage()).build();
        }
        return result;
    }

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
        JSONResult result = null;
        try {
            boolean success = faaSServiceCommandService.offlinePublish(id);
            result = JSONResult.Builder.builder().isSuccess(true).data(success).build();
        } catch (ParamAccessException e) {
            result = JSONResult.Builder.builder().isSuccess(false).message(e.getMessage()).build();
        } catch (BizException e) {
            result = JSONResult.Builder.builder().isSuccess(false).message(e.getMessage()).build();
        } catch (LockedException e) {
            result = JSONResult.Builder.builder().isSuccess(false).message(e.getMessage()).build();
        }
        return result;
    }

    @RequestMapping("service/publish/pre")
    public JSONResult prePublish(@RequestParam("id") Integer id) {
        JSONResult result = null;
        try {
            boolean success = faaSServiceCommandService.prePublish(id);
            result = JSONResult.Builder.builder().isSuccess(true).data(success).build();
        } catch (ParamAccessException e) {
            result = JSONResult.Builder.builder().isSuccess(false).message(e.getMessage()).build();
        } catch (BizException e) {
            result = JSONResult.Builder.builder().isSuccess(false).message(e.getMessage()).build();
        } catch (LockedException e) {
            result = JSONResult.Builder.builder().isSuccess(false).message(e.getMessage()).build();
        }
        return result;
    }

    @RequestMapping("service/publish/online")
    public JSONResult onlinePublish(@RequestParam("id") Integer id) {
        JSONResult result = null;
        try {
            boolean success = faaSServiceCommandService.online(id);
            result = JSONResult.Builder.builder().isSuccess(true).data(success).build();
        } catch (ParamAccessException e) {
            result = JSONResult.Builder.builder().isSuccess(false).message(e.getMessage()).build();
        } catch (BizException e) {
            result = JSONResult.Builder.builder().isSuccess(false).message(e.getMessage()).build();
        } catch (LockedException e) {
            result = JSONResult.Builder.builder().isSuccess(false).message(e.getMessage()).build();
        }
        return result;
    }

    @RequestMapping("service/publish/rollback")
    public JSONResult rollback(@RequestParam("id") Integer id, @RequestParam("toId") Integer toId) {
        JSONResult result = null;
        try {
            boolean success = faaSServiceCommandService.rollback(id, toId);
            result = JSONResult.Builder.builder().isSuccess(true).data(success).build();
        } catch (ParamAccessException e) {
            result = JSONResult.Builder.builder().isSuccess(false).message(e.getMessage()).build();
        } catch (BizException e) {
            result = JSONResult.Builder.builder().isSuccess(false).message(e.getMessage()).build();
        } catch (LockedException e) {
            result = JSONResult.Builder.builder().isSuccess(false).message(e.getMessage()).build();
        }
        return result;
    }
}
