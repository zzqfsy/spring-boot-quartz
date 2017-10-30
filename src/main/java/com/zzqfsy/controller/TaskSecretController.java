package com.zzqfsy.controller;

import com.zzqfsy.api.req.datatables.Columns;
import com.zzqfsy.api.resp.ResponseBodyInfo;
import com.zzqfsy.api.resp.ResultInfo;
import com.zzqfsy.service.task.TaskService;
import com.zzqfsy.service.task.domain.TaskInfo;
import com.zzqfsy.utils.ListUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tasks")
public class TaskSecretController {
    private static final String SECRET = "zzqfsySaveSecret2017";
    @Autowired
    private TaskService taskService;

    /**
     * 保存定时任务
     * @param info
     */
    @ApiOperation(value = "保存定时任务", notes = "")
    @ResponseBody
    @RequestMapping(value="/save", method= RequestMethod.POST)
    public ResponseBodyInfo tasksave(TaskInfo info, @RequestParam(value = "key", required = false) String key){
        if (!SECRET.equals(key)) return new ResponseBodyInfo(-1, "error secret", "");
        try {
            if(info.getId() == 0) {
                taskService.add(info);
            }else{
                taskService.edit(info);
            }
        } catch (RuntimeException e) {
            return ResultInfo.error(-1, e.getMessage());
        }
        return ResultInfo.success();
    }

    @ResponseBody
    @RequestMapping(value="delete", method= RequestMethod.POST)
    public ResponseBodyInfo delete(TaskInfo taskInfo, @RequestParam(value = "key", required = false) String key){
        if (!SECRET.equals(key)) return new ResponseBodyInfo(-1, "error secret", "");
        try {
            taskService.delete(taskInfo);
        } catch (RuntimeException e) {
            return ResultInfo.error(-1, e.getMessage());
        }
        return ResultInfo.success();
    }


    @ApiOperation(value = "任务列表", notes = "")
    @ResponseBody
    @RequestMapping(value="/", method= RequestMethod.GET)
    public Map<String, Object> tasks(Columns columns){
        String jobGroup = null;
        List<Map<Columns.Column, String>> columnsTemps = columns.getColumns();
        for (Map<Columns.Column, String> columnsTemp: columnsTemps) {
            if ("jobGroup".equals(columnsTemp.get(Columns.Column.name))){
                String data = columnsTemp.get(Columns.Column.searchValue);
                if (!StringUtils.isEmpty(data)) {
                    jobGroup = data;
                    break;
                }
            }
        }

        Map<String, Object> map = new HashMap<>();
        List<TaskInfo> resultList = taskService.list(jobGroup);
        int size = resultList.size();
        resultList = ListUtils.pageList(resultList, columns.getStart(), columns.getLength());

        map.put("data", resultList);
        map.put("draw", columns.getDraw());
        map.put("recordsTotal", size);
        map.put("recordsFiltered", size);
        return map;
    }


}
