package com.muhacha.customs.service;


import com.muhacha.customs.model.Notification;

/**
 * Created by cleophas on 2018/10/21.
 */
public interface NotificationService {

    void sendMessage(Notification message);
}
