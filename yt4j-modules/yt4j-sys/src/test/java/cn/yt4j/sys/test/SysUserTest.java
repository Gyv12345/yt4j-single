/*
 *    Copyright (c) [2020] [yang1989]
 *    [yt4j] is licensed under Mulan PSL v2.
 *    You can use this software according to the terms and conditions of the Mulan PSL v2.
 *    You may obtain a copy of Mulan PSL v2 at:
 *             http://license.coscl.org.cn/MulanPSL2
 *    THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *    See the Mulan PSL v2 for more details.
 */

package cn.yt4j.sys.test;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.yt4j.sys.entity.SysUser;
import cn.yt4j.sys.entity.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class SysUserTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mvc;

	@Test
	public void login() throws Exception {
		UserDTO dto = new UserDTO();
		dto.setUsername("admin");
		dto.setPassword("123456");
		MvcResult result = mvc
				.perform(post("/user/login").content(objectMapper.writeValueAsString(dto))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("200")).andReturn();
		String content = result.getResponse().getContentAsString();
		System.out.println(content);
	}

	@Test
	public void add() throws Exception {
		SysUser user = new SysUser();

		user.setUsername("test1989");
		user.setPassword("123456");
		String url = "/user/insert";
		mvc.perform(post(url).header("Authorization",
				"Bearer eyJhbGciOiJIUzM4NCJ9.eyJpc3MiOiJ5dDRqLmNuIiwic3ViIjoiYWRtaW4iLCJhdWQiOiJ2aXAiLCJleHAiOjE2NDA1MjY1MDIsIm5iZiI6MTYzOTYyNDQyNSwiaWF0IjoxNjM5NjI1MzI1fQ._6V08dcYzAuQjsQAfhf364IRHGQZBE1dSzOCPf6lLkdeGw01mvDEpPBwUhHaBqD1")
				.content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("200"));
	}

}
