package com.bingosoft.subscribe.rocketmq;

import static com.bingosoft.common.rocketmq.config.RocketmqProperties.PREFIX;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.bingosoft.common.rocketmq.annotation.RocketmqEvent;
import com.bingosoft.core.mysql.service.IOrderService;




@Component
public class Customer {
	
	@Autowired
	IOrderService orderService;
	
	@EventListener(condition = "#event.topic=='TopicTest1' && #event.tag=='TagA'")
	public void rocketmqMsgListen(RocketmqEvent event) {
		DefaultMQPushConsumer consumer = event.getConsumer();
		try {
			
			orderService.getOrderInRocketmq(event.getMsg("gbk"));
			System.out.println("com.bqjr.rocketmq.demo.msgConsumerDemo监听到一个消息达到：" + event.getMsg("gbk"));
			//TODO 进行业务处理
		} catch (Exception e) {
			if(event.getMessageExt().getReconsumeTimes()<=3){//重复消费3次
				try {
					consumer.sendMessageBack(event.getMessageExt(), 2);
				} catch (Exception e1) {
					//TODO 消息消费失败，进行日志记录
				}
			} else {
				//TODO 消息消费失败，进行日志记录
				
			}
		}
	}
}
