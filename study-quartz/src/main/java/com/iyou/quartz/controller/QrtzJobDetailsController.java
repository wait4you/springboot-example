package com.iyou.quartz.controller;

import com.iyou.quartz.controller.response.Response;
import com.iyou.quartz.service.QrtzJobDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author weiguohua
 * @since 2019-11-23
 */
@Api(tags = "定时任务查询", value = "job_details表")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/qrtz-job-details")
public class QrtzJobDetailsController {
    private final QrtzJobDetailsService qrtzJobDetailsService;

    @ApiOperation("查询全部")
    @GetMapping("/all")
    public Response queryAll() {
        return Response.success(qrtzJobDetailsService.queryAll());
    }

    @ApiOperation("根据Group查询全部")
    @GetMapping("/{group}")
    public Response queryByGroup(@PathVariable("group") String group) {
        return Response.success(qrtzJobDetailsService.queryByGroup(group));
    }

    @ApiOperation("根据Name和Group查询全部")
    @GetMapping("/{group}/{name}")
    public Response queryByGroupAndName(@PathVariable("group") String group,
                                        @PathVariable("name") String name) {
        return Response.success(qrtzJobDetailsService.queryByGroupAndName(group, name));
    }
}