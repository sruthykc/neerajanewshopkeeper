package com.diviso.graeshoppe.config;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

import com.diviso.graeshoppe.notification.avro.Notification;



public interface MessageBinderConfiguration {

	String NOTIFICATION="notification";

	@Input(NOTIFICATION)
	KStream<String, Notification> order();
	
	
}
