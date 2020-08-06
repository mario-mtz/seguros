package com.seguros.demo;

import java.io.Serializable;
import java.util.Map;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

/**
 * 
 * @author mario.martinez
 */
public class ViewScope implements Scope, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     * @param name
     * @param objectFactory
     * @return
     */
    @Override
    public Object get(String name,
            @SuppressWarnings("rawtypes") ObjectFactory objectFactory) {
        Map<String, Object> viewMap =
                FacesContext.getCurrentInstance().getViewRoot().getViewMap();

        if (viewMap.containsKey(name)) {
            return viewMap.get(name);
        } else {
            Object object = objectFactory.getObject();
            viewMap.put(name, object);
            return object;
        }
    }

    /**
     *
     * @param name
     * @return
     */
    @Override
    public Object remove(String name) {
        return FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(name);
    }

    /**
     *
     * @return
     */
    @Override
    public String getConversationId() {
        return null;
    }

    /**
     *
     * @param name
     * @param callback
     */
    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        //Not supported
    }

    /**
     *
     * @param key
     * @return
     */
    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }
}
