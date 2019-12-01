package com.iyou.study.controller;

import com.iyou.study.config.BaseResult;
import com.iyou.study.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Api(value = "用户管理", tags ="用户管理API", protocols = "http")
@RestController
@RequestMapping(value = "/user")
public class UserController {
    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<>());

    @ApiOperation(value = "获取用户列表", notes = "查询用户列表")
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    /*@ApiResponses({
            @ApiResponse(code = 100, message = "异常数据")
    })*/
    public List<User> getUserList() {
        return new ArrayList<>(users.values());
    }

    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public BaseResult<User> postUser(@RequestBody User user) {
        users.put(user.getId(), user);
        return BaseResult.successWithData(user);
    }

    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path",defaultValue = "111")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        return users.get(id);
    }

    @ApiOperation(value = "更新用户信息", notes = "根据用户ID更新信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = ""),
            @ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "age", value = "年龄", required = true, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public BaseResult<User> putUser(@PathVariable Long id, @RequestBody User user) {
        User u = users.get(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        users.put(id, u);
        return BaseResult.successWithData(u);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        users.remove(id);
        return "success";
    }

    @RequestMapping(value = "/ignoreMe/{id}", method = RequestMethod.DELETE)
    public String ignoreMe(@PathVariable Long id) {
        users.remove(id);
        return "success";
    }
}