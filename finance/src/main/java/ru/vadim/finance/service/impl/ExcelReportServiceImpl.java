package ru.vadim.finance.service.impl;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import ru.vadim.finance.client.BotClient;
import ru.vadim.finance.dto.request.ExcelReportRequest;
import ru.vadim.finance.dto.response.LiabilitiesAssetsResponse;
import ru.vadim.finance.entity.Category;
import ru.vadim.finance.service.CategoryService;
import ru.vadim.finance.service.ExcelReportService;
import ru.vadim.finance.service.LiabilitiesAssetsService;
import ru.vadim.finance.service.OperationService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@SuppressWarnings({"MagicNumber", "MultipleStringLiterals"})
public class ExcelReportServiceImpl implements ExcelReportService {
    private final CategoryService categoryService;
    private final LiabilitiesAssetsService liabilitiesAssetsService;
    private final OperationService operationService;
    private final static Logger LOGGER = LogManager.getLogger();
    private final BotClient botClient;

    @Override
    public void generateReport(Long chatId) {
        new Thread(() -> createReport(chatId)).start();
    }

    private void createReport(Long chatId) {
        try (Workbook workbook = new XSSFWorkbook()) {
            generateOperationsSheet(workbook, chatId);
            generateLiabilitiesAssetsSheet(workbook, chatId);
            botClient.excelReport(
                    new ExcelReportRequest(chatId, writeWorkbookToByteArray(workbook)));
        } catch (IOException e) {
            LOGGER.info(String.format("chatId = %s, %s", chatId, e.getMessage()));
        }
    }

    private void generateOperationsSheet(Workbook workbook, Long chatId) {
        Sheet sheet = workbook.createSheet("Операции");
        Row headerRow = sheet.createRow(0);
        createHeader(headerRow, "Категории", "Дата", "Тип", "Сумма");

        int rowIndex = 1;
        List<Category> categories = categoryService.findAllByChatId(chatId).stream()
                .map(dto -> {
                    Category category = new Category();
                    category.setTitle(dto.title());
                    category.setCategoryId(dto.categoryId());
                    return category;
                })
                .toList();

        for (Category category : categories) {
            Map<String, Map<String, Integer>> groupedOperations =
                    operationService.groupOperationsByDateAndType(category.getCategoryId());

            for (var dateEntry : groupedOperations.entrySet()) {
                String date = dateEntry.getKey();
                for (var typeEntry : dateEntry.getValue().entrySet()) {
                    String type = typeEntry.getKey();
                    Integer sum = typeEntry.getValue();

                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(category.getTitle());
                    row.createCell(1).setCellValue(date);
                    row.createCell(2).setCellValue(type);
                    row.createCell(3).setCellValue(sum);
                }
            }
        }

        calculateTotalSum(sheet, rowIndex);
    }

    private void generateLiabilitiesAssetsSheet(Workbook workbook, Long chatId) {
        Sheet sheet = workbook.createSheet("Активы и пассивы");
        Row headerRow = sheet.createRow(0);
        createHeader(headerRow, "Название", "Тип", "Сумма");

        List<LiabilitiesAssetsResponse> items = liabilitiesAssetsService.findAllByChatId(chatId);
        int rowIndex = 1;
        for (LiabilitiesAssetsResponse item : items) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(item.title());
            row.createCell(1).setCellValue(item.type());
            row.createCell(2).setCellValue(item.sum());
        }
    }

    private void createHeader(Row row, String... headers) {
        for (int i = 0; i < headers.length; i++) {
            row.createCell(i).setCellValue(headers[i]);
        }
    }

    private void calculateTotalSum(Sheet sheet, int rowIndex) {
        Row totalRow = sheet.createRow(rowIndex);
        totalRow.createCell(3).setCellValue("Итого");
        String formula = String.format("SUM(E2:E%d)", rowIndex);
        totalRow.createCell(4).setCellFormula(formula);
    }

    private byte[] writeWorkbookToByteArray(Workbook workbook) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }
}
