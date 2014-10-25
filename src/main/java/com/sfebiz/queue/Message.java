package com.sfebiz.queue;

import java.util.Properties;


/**
 * 消息类
 */
public class Message {
    static class SystemPropKey {
        public static final String TAG = "__TAG";
        public static final String KEY = "__KEY";
        public static final String MSGID = "__MSGID";
        public static final String RECONSUMETIMES = "__RECONSUMETIMES";
    }


    /**
     * 消息主题
     */
    private String topic;
    /**
     * 系统属性
     */
    Properties systemProperties;
    /**
     * 用户属性
     */
    private Properties userProperties;
    /**
     * 消息体
     */
    private byte[] body;


    public Message(String topic, String tag, String key, byte[] body) {
        this.topic = topic;
        this.body = body;

        this.putSystemProperties(SystemPropKey.TAG, tag);
        this.putSystemProperties(SystemPropKey.KEY, key);
    }


    void putSystemProperties(final String key, final String value) {
        if (null == this.systemProperties) {
            this.systemProperties = new Properties();
        }

        if (key != null && value != null) {
            this.systemProperties.put(key, value);
        }
    }


    String getSystemProperties(final String key) {
        if (null != this.systemProperties) {
            return this.systemProperties.getProperty(key);
        }

        return null;
    }


    public void putUserProperties(final String key, final String value) {
        if (null == this.userProperties) {
            this.userProperties = new Properties();
        }

        if (key != null && value != null) {
            this.userProperties.put(key, value);
        }
    }


    public String getUserProperties(final String key) {
        if (null != this.userProperties) {
            return (String) this.userProperties.get(key);
        }

        return null;
    }


    public String getTopic() {
        return topic;
    }


    public void setTopic(String topic) {
        this.topic = topic;
    }


    public String getTag() {
        return this.getSystemProperties(SystemPropKey.TAG);
    }


    public void setTag(String tag) {
        this.putSystemProperties(SystemPropKey.TAG, tag);
    }


    public String getKey() {
        return this.getSystemProperties(SystemPropKey.KEY);
    }


    public void setKey(String key) {
        this.putSystemProperties(SystemPropKey.KEY, key);
    }


    public String getMsgID() {
        return this.getSystemProperties(SystemPropKey.MSGID);
    }


    public void setMsgID(String msgid) {
        this.putSystemProperties(SystemPropKey.MSGID, msgid);
    }


    Properties getSystemProperties() {
        return systemProperties;
    }


    void setSystemProperties(Properties systemProperties) {
        this.systemProperties = systemProperties;
    }


    public Properties getUserProperties() {
        return userProperties;
    }


    public void setUserProperties(Properties userProperties) {
        this.userProperties = userProperties;
    }


    public byte[] getBody() {
        return body;
    }


    public void setBody(byte[] body) {
        this.body = body;
    }


    public void setReconsumeTimes(final int value) {
        putSystemProperties(SystemPropKey.RECONSUMETIMES, String.valueOf(value));
    }


    public int getReconsumeTimes() {
        String pro = this.getSystemProperties(SystemPropKey.RECONSUMETIMES);
        if (pro != null) {
            return Integer.parseInt(pro);
        }

        return 0;
    }


    @Override
    public String toString() {
        return "Message [topic=" + topic + ", systemProperties=" + systemProperties + ", userProperties="
                + userProperties + ", body=" + (body != null ? body.length : 0) + "]";
    }
}
