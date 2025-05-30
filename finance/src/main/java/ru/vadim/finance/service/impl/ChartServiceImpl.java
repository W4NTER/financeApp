package ru.vadim.finance.service.impl;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Service;
import ru.vadim.finance.client.BotClient;
import ru.vadim.finance.dto.request.ChartRequest;
import ru.vadim.finance.dto.response.CategoryResponseDTO;
import ru.vadim.finance.service.CategoryService;
import ru.vadim.finance.service.ChartService;
import ru.vadim.finance.service.OperationService;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ChartServiceImpl implements ChartService {
    private final CategoryService categoryService;
    private final OperationService operationService;
    private final BotClient botClient;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    @SneakyThrows
    @Override
    public void generateChart(Long chatId) {
        List<CategoryResponseDTO> categories = categoryService.findAllByChatId(chatId);
        var groupedOperations = categories.stream().map(category ->
                operationService.groupOperationsByDateAndType(category.categoryId())).toList();
        for (var dataset : groupedOperations) {
            new Thread(() -> {
                try {
                    createChart(dataset, chatId);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    private void createChart(Map<String, Map<String, Integer>> groupedOperations, Long chatId) throws IOException {
        DefaultCategoryDataset dataset = getDefaultCategoryDataset(groupedOperations);
        JFreeChart chart = ChartFactory.createBarChart(
                "Доходы и Расходы по дням",
                "Дата",
                "Сумма",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.BLACK);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ChartUtils.writeChartAsPNG(byteArrayOutputStream, chart, WIDTH, HEIGHT);

        botClient.sendChart(new ChartRequest(chatId, byteArrayOutputStream.toByteArray()));
    }

    private static DefaultCategoryDataset getDefaultCategoryDataset(
            Map<String, Map<String, Integer>> groupedOperations) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Map<String, Integer>> entry : groupedOperations.entrySet()) {
            String date = entry.getKey();
            Map<String, Integer> types = entry.getValue();

            Integer income = types.getOrDefault("INCOME", 0);
            dataset.addValue(income, "Доход", date);

            Integer outcome = types.getOrDefault("OUTCOME", 0);
            dataset.addValue(outcome, "Расход", date);
        }
        return dataset;
    }
}
