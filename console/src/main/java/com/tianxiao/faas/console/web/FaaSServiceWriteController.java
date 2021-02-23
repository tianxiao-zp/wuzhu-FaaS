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

@RestController
public class FaaSServiceWriteController {
    @Resource
    private FaaSServiceCommandService faaSServiceCommandService;
    @Resource
    private FaaSServiceDomainFactory faaSServiceDomainFactory;


    @RequestMapping("/api/faas/service/edit")
    public String edit(@RequestParam("id")Integer id) {
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
        return result.toString();
    }

    @RequestMapping("/api/faas/service/save")
    public String save(FaaSServiceSaveCommand param) {
        JSONResult result = null;
        try {
            boolean save = faaSServiceCommandService.save(param);
            String tips = save ? "保存成功" : "保存失败,请刷新重试";
            result =JSONResult.Builder.builder().data(save).isSuccess(save).message(tips).build();
        } catch (ParamAccessException e) {
            result =JSONResult.Builder.builder().isSuccess(false).message(e.getMessage()).build();
        } catch (BizException e) {
            result =JSONResult.Builder.builder().isSuccess(false).message(e.getMessage()).build();
        }
        return result.toString();
    }

    @RequestMapping("/api/faas/service/offline")
    public String offlinePublish(@RequestParam("id") Integer id) {
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
        return result.toString();
    }

    @RequestMapping("/api/faas/service/publish/pre")
    public String prePublish(@RequestParam("id") Integer id) {
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
        return result.toString();
    }

    @RequestMapping("/api/faas/service/publish/online")
    public String onlinePublish(@RequestParam("id") Integer id) {
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
        return result.toString();
    }

    @RequestMapping("/api/faas/service/publish/rollback")
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
