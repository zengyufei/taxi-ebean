package com.ys.admin.base.shiroRealm;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * 不创建 session 设置
 */
public class CustomSubjectFactory extends DefaultWebSubjectFactory {

    @Override
	public Subject createSubject(SubjectContext context) {
        //不创建session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
