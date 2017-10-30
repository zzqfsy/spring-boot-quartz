package com.zzqfsy.controller;

import com.zzqfsy.api.resp.ResponseBodyInfo;
import com.zzqfsy.api.resp.ResultInfo;
import com.zzqfsy.service.task.TaskService;
import com.zzqfsy.service.task.domain.TaskInfo;
import com.zzqfsy.utils.ListUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务管理
 * @author zzqfsy
 */
@Controller
public class TaskManageController {
	@Autowired
	private TaskService taskService;

	/**
	 * Index.jsp
	 */
	@ApiOperation(value = "主页", notes = "")
	@RequestMapping(value={"", "/", "index"}, method = RequestMethod.GET)
	public String info(){
		return "index";
	}

	/**
	 * 任务列表
	 * @return_
	 */
	@ApiOperation(value = "任务列表", notes = "")
	@ResponseBody
	@RequestMapping(value="list", method= RequestMethod.POST)
	public Map<String, Object> list(@RequestParam(value = "page", required = true) Integer page, @RequestParam(value = "rows", required = true) Integer rows,
						@RequestParam(value = "jobGroup", required = false) String jobGroup){
		Map<String, Object> map = new HashMap<>();
		List<TaskInfo> infos = taskService.list(jobGroup);
		List<TaskInfo> resultList = ListUtils.pageList(infos, ListUtils.getStartByPageNo(page, rows), rows);
		map.put("rows", resultList);
		map.put("total", infos.size());
		return map;
	}

	/**
	 * 指定任务
	 * @return_
	 */
	@ApiOperation(value = "指定任务", notes = "")
	@ResponseBody
	@RequestMapping(value="detail", method= RequestMethod.GET)
	public TaskInfo detail(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup){
		return taskService.detail(jobName, jobGroup);
	}

	/**
	 * 保存定时任务
	 * @param info
	 */
	@ApiOperation(value = "保存定时任务", notes = "")
	@ResponseBody
	@RequestMapping(value="save", method= RequestMethod.POST)
	public ResponseBodyInfo save(TaskInfo info){
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

	/**
	 * 删除定时任务
	 * @param taskInfo
	 * @return
     */
	@ApiOperation(value = "删除定时任务", notes = "")
	@ResponseBody
	@RequestMapping(value="delete", method= RequestMethod.POST)
	public ResponseBodyInfo delete(TaskInfo taskInfo){
		try {
			taskService.delete(taskInfo);
		} catch (RuntimeException e) {
			return ResultInfo.error(-1, e.getMessage());
		}
		return ResultInfo.success();
	}

	/**
	 * 暂停定时任务
	 * @param taskInfo
	 * @return
     */
	@ApiOperation(value = "暂停定时任务", notes = "")
	@ResponseBody
	@RequestMapping(value="pause", method = RequestMethod.POST)
	public ResponseBodyInfo pauseA(TaskInfo taskInfo){
		try {
			taskService.pause(taskInfo);
		} catch (RuntimeException e) {
			return ResultInfo.error(-1, e.getMessage());
		}
		return ResultInfo.success();
	}

	/**
	 * 重新开始定时任务
	 * @param taskInfo
	 * @return
     */
	@ApiOperation(value = "重新开始定时任务", notes = "")
	@ResponseBody
	@RequestMapping(value="resume", method = RequestMethod.POST)
	public ResponseBodyInfo resumeA(TaskInfo taskInfo){
		try {
			taskService.resume(taskInfo);
		} catch (RuntimeException e) {
			return ResultInfo.error(-1, e.getMessage());
		}
		return ResultInfo.success();
	}
}
