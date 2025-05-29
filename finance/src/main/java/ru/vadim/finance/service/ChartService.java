package ru.vadim.finance.service;

import java.io.IOException;

//TODO полностью переделать реализацию
public interface ChartService {
    void generateChart(Long chatId) throws IOException;
}
