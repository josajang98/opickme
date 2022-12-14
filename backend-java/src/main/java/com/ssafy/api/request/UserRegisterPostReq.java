package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 유저 회원가입 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("UserRegisterPostRequest")
public class UserRegisterPostReq {
	@NotBlank
	@ApiModelProperty(name="유저 ID", example="ssafy_web")
	String username;

	@NotBlank
	@ApiModelProperty(name="유저 Password", example="your_password")
	String password;

	@Email
	@ApiModelProperty(name="유저 email", example="user@naver.com")
	String email;

	@NotBlank
	@ApiModelProperty(name="유저 nickname", example="nickname")
	String nickname;


	@ApiModelProperty(name="유저 role", example="student")
	String role="student";

}
