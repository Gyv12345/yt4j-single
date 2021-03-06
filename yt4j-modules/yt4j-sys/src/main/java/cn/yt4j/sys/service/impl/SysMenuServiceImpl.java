/*
 *    Copyright (c) [2020] [yang1989]
 *    [yt4j] is licensed under Mulan PSL v2.
 *    You can use this software according to the terms and conditions of the Mulan PSL v2.
 *    You may obtain a copy of Mulan PSL v2 at:
 *             http://license.coscl.org.cn/MulanPSL2
 *    THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *    See the Mulan PSL v2 for more details.
 */

package cn.yt4j.sys.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.yt4j.core.constant.SysConstants;
import cn.yt4j.core.util.TreeUtil;
import cn.yt4j.sys.dao.SysMenuDao;
import cn.yt4j.sys.entity.SysMenu;
import cn.yt4j.sys.entity.vo.MenuTreeVO;
import cn.yt4j.sys.entity.vo.Meta;
import cn.yt4j.sys.entity.vo.Route;
import cn.yt4j.sys.entity.vo.TopMenuVO;
import cn.yt4j.sys.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 资源权限(SysMenu)表服务实现类
 *
 * @author gyv12345@163.com
 * @since 2020-08-10 08:43:33
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenu> implements SysMenuService {

	@Override
	public List<Route> nav(Long id, Long applicationId) {
		return Optional.ofNullable(this.baseMapper.listMenuByUserIdAndApplicationId(id, applicationId))
				.orElse(new ArrayList<>()).stream().map(sysMenu -> {
					Route route = new Route();
					route.setPath(sysMenu.getPath());
					route.setId(sysMenu.getId());
					route.setParentId(sysMenu.getParentId());
					route.setName(sysMenu.getLabel());
					route.setHidden(sysMenu.getHidden());
					route.setComponent(sysMenu.getComponent());
					route.setHideChildrenInMenu(false);
					Meta meta = new Meta();
					meta.setIcon(sysMenu.getIcon());
					meta.setTitle(sysMenu.getTitle());
					meta.setShow(sysMenu.getHidden());
					meta.setPermission(
							Arrays.asList(Optional.ofNullable(sysMenu.getPermission()).orElse("").split(",")));
					route.setMeta(meta);
					return route;
				}).collect(Collectors.toList());
	}

	@Override
	public List<MenuTreeVO> menuTree() {
		return TreeUtil
				.buildByRecursive(
						Optional.ofNullable(this.list()).orElse(new ArrayList<>()).stream()
								.map(sysMenu -> new MenuTreeVO(sysMenu.getId(), sysMenu.getParentId(),
										sysMenu.getTitle(), sysMenu.getTitle(), sysMenu.getIcon(), sysMenu.getOrderNo(),
										sysMenu.getPath(), sysMenu.getComponent()))
								.collect(Collectors.toList()),
						SysConstants.PUBLIC_PARENT_ID);
	}

	@Override
	public List<TopMenuVO> topMenu() {
		return this.baseMapper.listTopMenu(StpUtil.getLoginIdAsLong()).stream().map(sysMenu -> {
			TopMenuVO vo = new TopMenuVO();
			vo.setTitle(sysMenu.getTitle());
			vo.setId(sysMenu.getId());
			vo.setRedirect(sysMenu.getRedirect());
			return vo;
		}).collect(Collectors.toList());
	}

}
