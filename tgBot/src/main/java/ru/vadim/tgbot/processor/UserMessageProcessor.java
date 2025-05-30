package ru.vadim.tgbot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.AbstractSendRequest;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;

public interface UserMessageProcessor {
   BaseRequest<?, ?> process(Update update);
}
