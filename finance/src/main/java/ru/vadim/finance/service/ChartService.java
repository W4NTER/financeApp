package ru.vadim.finance.service;

import java.io.IOException;

public interface ChartService {
    void generateChart(Long chatId) throws IOException;
}
