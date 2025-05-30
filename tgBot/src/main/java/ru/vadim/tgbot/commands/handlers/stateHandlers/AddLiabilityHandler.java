package ru.vadim.tgbot.commands.handlers.stateHandlers;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vadim.tgbot.client.LiabilitiesAssetsWebClient;
import ru.vadim.tgbot.dto.request.LiabilitiesAssetsRequest;
import ru.vadim.tgbot.utils.state.StateType;

import static ru.vadim.tgbot.utils.constants.BotAnswersConstants.EVERYTHING_WRITE_BOT;
import static ru.vadim.tgbot.utils.constants.BotAnswersConstants.SOMETHING_WRONG_ANSWER_FROM_BOT;
import static ru.vadim.tgbot.utils.constants.Constants.LIABILITY_TYPE;

@Component
@AllArgsConstructor
public class AddLiabilityHandler implements StateHandler {
    private final LiabilitiesAssetsWebClient liabilitiesAssetsWebClient;

    @Override
    public boolean supports(String state) {
        return StateType.ADD_LIABILITY.toString().equals(state);
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        String[] message = update.message().text().split("-");
        try {
            liabilitiesAssetsWebClient.addLiabilityOrAsset(
                    new LiabilitiesAssetsRequest(
                            chatId,
                            message[0],
                            LIABILITY_TYPE,
                            Integer.parseInt(message[1].trim()))
            );
            return new SendMessage(chatId, EVERYTHING_WRITE_BOT);
        } catch (Exception e) {
            return new SendMessage(chatId, SOMETHING_WRONG_ANSWER_FROM_BOT);
        }
    }
}
