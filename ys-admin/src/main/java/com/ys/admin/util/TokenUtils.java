package com.ys.admin.util;

import com.ys.admin.base.realm.Token;
import com.ys.common.entitys.rbac.SysMember;
import io.ebean.Ebean;
import io.ebeaninternal.server.util.Md5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;

public class TokenUtils {

	public static long timeout = 2 * 60 * 60;
	public static long liveTime = 1 * 60 * 60;

	@Value("${sys.token.timeout}")
	public void setTimeout(long timeout) {
		TokenUtils.timeout = timeout;
	}

	@Value("${sys.token.liveTime}")
	public void setLiveTime(long liveTime) {
		TokenUtils.liveTime = liveTime;
	}

	/**
	 * 组装自己的tocken
	 * 用户id,long型超时时间，用户名, MD5校对码。
	 */
	public static String getTokenStr(SysMember sysMember) throws Exception {
		if (sysMember == null || StringUtils.isBlank(sysMember.getAccount()) || StringUtils.isBlank(sysMember.getAccount())) {
			return "";
		}
		long memberId = sysMember.getId();
		String account = sysMember.getAccount();
		long expireTime = Instant.now().plusSeconds(timeout).toEpochMilli();
		String tocken = AesSecret.encrypt(memberId + "|" + expireTime + "|" + account + "|" + Md5.hash(memberId + expireTime + account));
		return tocken;
	}

	public static Token getToken(String tokenStr) throws Exception {
		if (StringUtils.isBlank(tokenStr)) {
			return null;
		}

		String content = AesSecret.decrypt(tokenStr);
		long memberId = Long.parseLong(StringUtils.split(content, "|")[0]);
		long expireTime = Long.parseLong(StringUtils.split(content, "|")[1]);
		String account = StringUtils.split(content, "|")[2];
		String md5Code = StringUtils.split(content, "|")[3];

		if (!md5Code.equals(Md5.hash(memberId + expireTime + account))) {//MD5校对，防止伪装token
			return null;
		}

		long nowTime = Instant.now().toEpochMilli();
		if (nowTime > expireTime) {
			return null;
		}

		return new Token(memberId, expireTime, account, md5Code);
	}

	public static SysMember getMember(Token token) {
		return Ebean.find(SysMember.class, token.getMemberId());
	}

}
