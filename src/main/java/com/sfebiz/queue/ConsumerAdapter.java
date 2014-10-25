package com.sfebiz.queue;

import java.util.Properties;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

public class ConsumerAdapter {
	private Properties properties = new Properties();
	private volatile Consumer consumer = null;
     
	private String consumerId = null;
	private String accessKey = null;
	private String secretKey = null;
	
	public ConsumerAdapter setConsumerId(String consumerId) {
		this.consumerId = consumerId;
		return this;
	}

	public ConsumerAdapter  setAccessKey(String accessKey) {
		this.accessKey = accessKey;
		return this;
	}

	public ConsumerAdapter  setSecretKey(String secretKey) {
		this.secretKey = secretKey;
		return this;
	}

	public synchronized void start () {
		if (consumer != null) {
			return ;
		}
		properties.put(PropertyKeyConst.ConsumerId, this.consumerId);
	    properties.put(PropertyKeyConst.AccessKey, this.accessKey);
	    properties.put(PropertyKeyConst.SecretKey, this.secretKey);
	    consumer = ONSFactory.createConsumer(properties);
	    consumer.start();
	}
	
	public void consum(String topic, String tag, final com.sfebiz.queue.MessageListener listener) {
		consumer.subscribe(topic, tag, new MessageListener() {
            public Action consume(Message onsMessage, ConsumeContext context) {
            	com.sfebiz.queue.Message message = new com.sfebiz.queue.Message(onsMessage.getTopic(), onsMessage.getTag(), onsMessage.getKey(), onsMessage.getBody());
            	message.setUserProperties(onsMessage.getUserProperties());
            	message.setReconsumeTimes(onsMessage.getReconsumeTimes());
            	com.sfebiz.queue.Action action = listener.consume(message);
            	if (action.equals(Action.CommitMessage)) {
            		return Action.CommitMessage;		
            	} else {
            		return Action.ReconsumeLater;
            	}
            }
        });
	}
	
	public synchronized void destory () {
		if (consumer != null) {
		// 在应用退出前，销毁Producer对象
        // 注意：如果不销毁也没有问题
			consumer.shutdown();
		}
	}
}
