package com.diviso.graeshoppe.web.rest;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.corundumstudio.socketio.SocketIOServer;
import com.diviso.graeshoppe.client.order.model.NotificationDTO;
import com.diviso.graeshoppe.config.MessageBinderConfiguration;
import com.diviso.graeshoppe.notification.avro.Notification;

@EnableBinding(MessageBinderConfiguration.class)
public class NotificationService {

	@Autowired
	private SocketIOServer socketIOServer;

	private final Logger LOG = LoggerFactory.getLogger(NotificationService.class);

	@StreamListener(MessageBinderConfiguration.NOTIFICATION)
	public void listenToNotifications(KStream<String, Notification> message) {
		message.foreach((key, value) -> {
			LOG.info("Notification Value consumed is " + value);
			if (value.getType().equals("Pending-Notification") || value.getType().equals("Confirmed-Notification")) {
				sendNotification(value);
			}
		});
	}

	private void sendNotification(Notification message) {
		LOG.info("Notification is send via socket server");
		NotificationDTO notificationDTO = new NotificationDTO();
		notificationDTO
				.setDate(OffsetDateTime.ofInstant(Instant.ofEpochMilli(message.getDate()), ZoneId.systemDefault()));
		notificationDTO.setTitle(message.getTitle());
		notificationDTO.setMessage(message.getMessage());
		notificationDTO.setTargetId(message.getTargetId());
		notificationDTO.setReceiverId(message.getReceiverId());
		notificationDTO.setId(message.getId());
		notificationDTO.setStatus(message.getStatus());
		notificationDTO.setImage(message.getImage().array());
		notificationDTO.setImageContentType(message.getImageContentType());
		socketIOServer.getBroadcastOperations().sendEvent(message.getReceiverId(), notificationDTO);
	}
}
