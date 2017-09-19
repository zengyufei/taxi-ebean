package com.zzsim.taxi.admin.util;

import com.zzsim.taxi.core.common.entitys.rbac.SysMember;
import io.ebeaninternal.server.util.Md5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;

public class TockenUtil {

    public static long timeout = 2 * 60 * 60;
    public static long liveTime = 1 * 60 * 60;

    @Value("${sys.token.timeout}")
    public void setTimeout(long timeout) {
        TockenUtil.timeout = timeout;
    }

    @Value("${sys.token.liveTime}")
    public void setLiveTime(long liveTime) {
        TockenUtil.liveTime = liveTime;
    }

    /**组装自己的tocken
     * 用户id,long型超时时间，用户名, MD5校对码。
     * */
    public static String getToken(SysMember sysMember) throws Exception {
        if (sysMember == null || StringUtils.isBlank(sysMember.getAccount())|| StringUtils.isBlank(sysMember.getAccount())) {
            return "";
        }
        long memberId = sysMember.getId();
        String account = sysMember.getAccount();
        long expireTime = Instant.now().plusSeconds(timeout).toEpochMilli();
        String tocken = AESSecret.encrypt(memberId + "|" + expireTime + "|" + account + "|" +  Md5.hash(memberId + expireTime + account));
        return tocken;
    }
}
