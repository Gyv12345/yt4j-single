/*
 *    Copyright (c) [2020] [yang1989]
 *    [yt4j] is licensed under Mulan PSL v2.
 *    You can use this software according to the terms and conditions of the Mulan PSL v2.
 *    You may obtain a copy of Mulan PSL v2 at:
 *             http://license.coscl.org.cn/MulanPSL2
 *    THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *    See the Mulan PSL v2 for more details.
 */

package cn.yt4j.sys.controller;

import cn.yt4j.core.domain.PageRequest;
import cn.yt4j.core.domain.PageResult;
import cn.yt4j.core.domain.R;
import cn.yt4j.core.util.PageUtil;
import cn.yt4j.log.annotation.SysLog;
import cn.yt4j.security.util.SecurityUtil;
import cn.yt4j.sys.entity.SysDict;
import cn.yt4j.sys.entity.SysUser;
import cn.yt4j.sys.entity.dto.PasswordDTO;
import cn.yt4j.sys.entity.dto.UserDTO;
import cn.yt4j.sys.entity.vo.UserInfo;
import cn.yt4j.sys.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户(SysUser)表控制层
 *
 * @author gyv12345@163.com
 * @since 2020-08-07 17:11:45
 */
@RequiredArgsConstructor
@Api(tags = "用户")
@RestController
@RequestMapping("/user")
public class SysUserController {

	/**
	 * 服务对象
	 */
	private final SysUserService sysUserService;

	@SysLog("登录")
	@ApiOperation("登录")
	@PostMapping("login")
	public R<String> login(@RequestBody @Valid UserDTO dto) {
		return R.ok(this.sysUserService.login(dto), "登录成功");
	}

	@ApiOperation("退出")
	@GetMapping("logout")
	public R logout() {
		this.sysUserService.logout();
		return R.ok("退出成功");
	}

	@ApiOperation("修改密码")
	@PostMapping("update/password")
	public R<Boolean> updatePassword(@RequestBody @Valid PasswordDTO dto) {
		return R.ok(this.sysUserService.updatePassword(dto));
	}

	@SysLog("登录")
	@ApiOperation("获取用户信息")
	@GetMapping("info")
	public R<UserInfo> getInfo() {
		return R.ok(this.sysUserService.getInfo(SecurityUtil.getUser().getId()));
	}

	/**
	 * 分页查询所有数据
	 * @param request 查询实体
	 * @return 所有数据
	 */
	@ApiOperation("列表 ")
	@PostMapping("page")
	public R<PageResult<SysUser>> listPage(@Valid @RequestBody PageRequest<SysUser> request) {
		return R.ok(this.sysUserService.page(request.page(),request.wrapper()));
	}

	/**
	 * 通过主键查询单条数据
	 * @param id 主键
	 * @return 单条数据
	 */
	@ApiOperation("按ID返回用户")
	@GetMapping("get/{id}")
	public R<SysUser> selectOne(@PathVariable Long id) {
		return R.ok(this.sysUserService.one(id));
	}

	/**
	 * 新增数据
	 * @param sysUser 实体对象
	 * @return 新增结果
	 */
	@ApiOperation("添加 ")
	@PostMapping("insert")
	public R insert(@RequestBody SysUser sysUser) {
		return R.ok(this.sysUserService.insert(sysUser));
	}

	/**
	 * 重置用户密码
	 * @param id 用户ID
	 * @return
	 */
	@ApiOperation("重置用户密码 ")
	@PostMapping("reset/password/{id}")
	public R resetPassword(@PathVariable Long id) {
		return R.ok(this.sysUserService.resetPassword(id));
	}

	/**
	 * 修改数据
	 * @param sysUser 实体对象
	 * @return 修改结果
	 */
	@ApiOperation("修改")
	@PutMapping("update")
	public R update(@RequestBody SysUser sysUser) {
		return R.ok(this.sysUserService.update(sysUser));
	}

	/**
	 * 删除数据
	 * @param id 主键结合
	 * @return 删除结果
	 */
	@ApiOperation("删除")
	@DeleteMapping("delete/{id}")
	public R delete(@PathVariable Long id) {
		return R.ok(this.sysUserService.removeById(id));
	}

}
