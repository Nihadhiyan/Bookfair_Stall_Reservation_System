package com.bookfair.backend.service;

import java.util.Map;

public interface NotificationChannel {
    void send(String recipient, String subject, String template, Map<String, Object> variables);

    boolean supports(String channelType);
}