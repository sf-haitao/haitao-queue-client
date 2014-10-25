package com.sfebiz.queue;

import java.util.Properties;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendResult;

public class ProducerAdapter {
	private final Properties properties = new Properties();
	private volatile Producer producer = null;
	private String producerId = null;
	private String accessKey = null;
	private String secretKey = null;
	
	public ProducerAdapter setProducerId(String producerId) {
		this.producerId = producerId;
		return this;
	}

	public ProducerAdapter setAccessKey(String accessKey) {
		this.accessKey = accessKey;
		return this;
	}

	public ProducerAdapter setSecretKey(String secretKey) {
		this.secretKey = secretKey;
		return this;
	}

	public synchronized void start () {
		if (producer != null) {
			return ;
		}
		properties.put(PropertyKeyConst.ProducerId, this.producerId);
	    properties.put(PropertyKeyConst.AccessKey, this.accessKey);
	    properties.put(PropertyKeyConst.SecretKey, this.secretKey);
		producer = ONSFactory.createProducer(properties);
	    producer.start();
	}
	
	/**
	 * 
	 * @param topic Message Topic
	 * @param tag Message Tag 可理解为Gmail中的标签，对消息进行再归类，方便Consumer指定过滤条件在ONS服务器过滤 
	 * @param key 设置代表消息的业务关键属性，请尽可能全局唯一。 以方便您在无法正常收到消息情况下，可通过ONS Console查询消息并补发。注意：不设置也不会影响消息正常收发
	 * @param body Message Body 任何二进制形式的数据，ONS不做任何干预，需要Producer与Consumer协商好一致的序列化和反序列化方式
	 * @return 只要不抛异常就是成功，返回消息ID 
	 */
	public String send (String topic, String tag, String key, byte[] body) {
		if (producer == null) {
			start();
		}
		Message msg = new Message(topic, tag, body);

		msg.setKey(key);

		SendResult sendResult = producer.send(msg);
		return sendResult.getMessageId();
	}
	
	public synchronized void destory () {
		if (producer != null) {
		// 在应用退出前，销毁Producer对象
        // 注意：如果不销毁也没有问题
			producer.shutdown();
		}
	}
}
