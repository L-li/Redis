package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.entity.User;
import com.service.UserService;
import com.util.TaskJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MyController {
    @Autowired
    private UserService userService;
    @Autowired
    private TaskJob taskJob;

    /**
     * 查询
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/find", produces = "application/json; charset=UTF-8")
    public @ResponseBody
    String find(HttpServletRequest httpServletRequest) {
        Integer id = Integer.parseInt(httpServletRequest.getParameter("id"));
        if (null != id) {
            try {
                User user = userService.selectByPrimaryKey(id);
                return JSONObject.toJSONString(user);
            } catch (Exception e) {
                return "缓存无数据";
            }
        } else {
            return "error";
        }
    }

    @RequestMapping(value = "/init", produces = "application/json; charset=UTF-8")
    public @ResponseBody
    String init() throws Exception {
        User user = new User();
        user.setName("redist1");
        user.setAge(100);
        userService.insertSelective(user);
        return "插入完成:" + JSONObject.toJSONString(user);
    }

    /**
     * 清理指定Id
     *
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/clear", produces = "application/json; charset=UTF-8")
    public @ResponseBody
    String clear(HttpServletRequest httpServletRequest) throws Exception {
        Integer id = Integer.parseInt(httpServletRequest.getParameter("id"));
        String res = userService.clearKey(id);
        return res;
    }

    /**
     * 清理全部缓存
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/clearALL", produces = "application/json; charset=UTF-8")
    public @ResponseBody
    String clear() throws Exception {
        taskJob.printTaskJob();
        return "清理缓存完成";
    }

}