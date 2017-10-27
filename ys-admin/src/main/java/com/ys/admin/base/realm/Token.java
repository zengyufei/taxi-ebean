package com.ys.admin.base.realm;

import lombok.Data;

@Data
public class Token {

	private Long memberId;
	private Long expireTime;
	private String account;
	private String md5Code;

	public Token(long memberId, long expireTime, String account, String md5Code) {
		this.memberId = memberId;
		this.expireTime = expireTime;
		this.account = account;
		this.md5Code = md5Code;
	}
}
