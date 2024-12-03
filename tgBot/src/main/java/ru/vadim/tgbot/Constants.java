package ru.vadim.tgbot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Constants {
    private Constants() {
    }

    public static final String[] MAIN_MENU_ARR = {"go to start"};
    public static final boolean RESIZE_KEYBOARD = true;
    public static final Logger LOGGER = LogManager.getLogger();
}
