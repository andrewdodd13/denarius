package org.ad13.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class CurrentContextBean implements ApplicationContextAware {
    private ApplicationContext context;
    
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        this.context = arg0;
    }

    public ApplicationContext getContext() {
        return context;
    }
}
