package ru.vadim.tgbot.cashService;

import ru.vadim.tgbot.utils.state.StateType;

public interface StateService {
    String getState(Long chatId);

    void setState(Long chatId, StateType state);
}
