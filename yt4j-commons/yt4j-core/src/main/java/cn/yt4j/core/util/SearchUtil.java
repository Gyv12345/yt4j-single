/*
 *    Copyright (c) [2020] [yang1989]
 *    [yt4j] is licensed under Mulan PSL v2.
 *    You can use this software according to the terms and conditions of the Mulan PSL v2.
 *    You may obtain a copy of Mulan PSL v2 at:
 *             http://license.coscl.org.cn/MulanPSL2
 *    THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *    See the Mulan PSL v2 for more details.
 */

package cn.yt4j.core.util;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName QueryWrapper自定义SQL查询构造器
 * @Author Lu Ze
 * @Date 2020/4/1
 * @Version V1.0
 **/
public class SearchUtil {

	private static final Pattern HUMP_PATTERN = Pattern.compile("[A-Z]");

	private static final String LINE = "_";

	/**
	 * eq：相等<br />
	 * ne：不等<br />
	 * like：两边模糊查询<br />
	 * leftLike：左边模糊查询<br />
	 * rightLike：右边模糊查询<br />
	 * notLike：不与%xx%相等<br />
	 * gt：大于<br />
	 * lt：小于<br />
	 * ge：大于等于<br />
	 * le：小于等于<br />
	 * bet：...之间 bet_age=15,20 notBet：不在...之间 netBet_age=15,20 or：在紧接着下一个条件使用，否则不生效<br />
	 * @param condition
	 * @return
	 */
	public static QueryWrapper parseWhereSql(Map<String, Object> condition) {
		QueryWrapper queryWrapper = new QueryWrapper();
		if (condition != null && condition.size() > 0) {
			condition.forEach((k, v) -> {
				if (k.contains(LINE) && StrUtil.isNotBlank(v.toString())) {
					String pre = k.substring(0, k.indexOf(LINE));
					String column = humpToLine(k.substring(k.indexOf(LINE) + 1));
					switch (pre) {
					case "eq":
						queryWrapper.eq(column, v);
						break;
					case "ne":
						queryWrapper.ne(column, v);
						break;
					case "like":
						queryWrapper.like(column, v);
						break;
					case "leftLike":
						queryWrapper.likeLeft(column, v);
						break;
					case "rightLike":
						queryWrapper.likeRight(column, v);
						break;
					case "notLike":
						queryWrapper.notLike(column, v);
						break;
					case "gt":
						queryWrapper.gt(column, v);
						break;
					case "lt":
						queryWrapper.lt(column, v);
						break;
					case "ge":
						queryWrapper.ge(column, v);
						break;
					case "le":
						queryWrapper.le(column, v);
						break;
					case "bet":
						String[] arr = v.toString().split(",");
						queryWrapper.between(column, arr[0], arr[1]);
						break;
					case "notBet":
						arr = v.toString().split(",");
						queryWrapper.notBetween(column, arr[0], arr[1]);
						break;
					case "or":
						queryWrapper.or(true);
						break;
					default:
						break;
					}
				}
			});
		}
		return queryWrapper;
	}

	/**
	 * 驼峰转下划线
	 * @param column
	 * @return
	 */
	private static String humpToLine(String column) {
		Matcher matcher = HUMP_PATTERN.matcher(column);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

}
