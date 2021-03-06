/*
 *    Copyright (c) [2020] [yang1989]
 *    [yt4j] is licensed under Mulan PSL v2.
 *    You can use this software according to the terms and conditions of the Mulan PSL v2.
 *    You may obtain a copy of Mulan PSL v2 at:
 *             http://license.coscl.org.cn/MulanPSL2
 *    THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *    See the Mulan PSL v2 for more details.
 */

package cn.yt4j.sa.service;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import cn.yt4j.sa.constant.SaConstants;
import cn.yt4j.sa.domain.SaUserCache;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义权限验证接口扩展
 *
 * @author shichenyang
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        SaUserCache userCache = getSaUserCache(loginId);
        return userCache.getPermissions();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        SaUserCache userCache = getSaUserCache(loginId);
        return userCache.getRoles();
    }

    private SaUserCache getSaUserCache(Object loginId) {
        String token = StpUtil.getTokenValueByLoginId(loginId);
        SaSession session = StpUtil.getTokenSessionByToken(token);
        SaUserCache userCache = session.getModel(SaConstants.SA_USER_PREFIX, SaUserCache.class);
        return userCache;
    }


}

