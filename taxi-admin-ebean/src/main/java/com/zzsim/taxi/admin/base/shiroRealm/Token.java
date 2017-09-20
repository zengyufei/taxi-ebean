package com.zzsim.taxi.admin.base.shiroRealm;

import lombok.Data;

@Data
public class Token {

	private long memberId;
	private long expireTime;
	private String account;
	private String md5Code;

	public Token(long memberId, long expireTime, String account, String md5Code) {
		this.memberId = memberId;
		this.expireTime = expireTime;
		this.account = account;
		this.md5Code = md5Code;
	}
}
