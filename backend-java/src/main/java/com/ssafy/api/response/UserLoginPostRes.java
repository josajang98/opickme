package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;

import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 로그인 API ([POST] /api/v1/auth) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("UserLoginPostResponse")
public class UserLoginPostRes extends BaseResponseBody{
	@ApiModelProperty(name="JWT 인증 토큰", example="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN...")
	String accessToken;

	Long id;
	String username;
	String email;
	String nickname;
	String role;
	// 생성자랑 다른게 뭐냐
	public static UserLoginPostRes of(Integer statusCode, String message, String accessToken, User user) {
		UserLoginPostRes res = new UserLoginPostRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setAccessToken(accessToken);

		res.setId(user.getId());
		res.setUsername(user.getUsername());
		res.setEmail(user.getEmail());
		res.setNickname(user.getNickname());
		res.setRole(user.getRole());
		return res;
	}
}
