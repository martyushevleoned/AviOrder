package org.example.utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.model.dto.OrderDto;
import org.springframework.stereotype.Component;

@Component
public class WorkbookUtils {

    public Workbook generateOrderWorkbook(OrderDto orderDto) {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("" + orderDto.orderId());

        Row columns = sheet.createRow(0);
        columns.createCell(0).setCellValue("Ссылка");
        columns.createCell(1).setCellValue("ПФ");
        columns.createCell(2).setCellValue("Кол-во дней");
        columns.createCell(3).setCellValue("Дата-Н");
        columns.createCell(4).setCellValue("Дата-К");
        columns.createCell(5).setCellValue("Контакты");
        columns.createCell(6).setCellValue("Цена за сутки");

        final int[] index = {1};
        orderDto.advertisementDtoList().forEach(advertisement -> {
            Row row = sheet.createRow(index[0]);
            row.createCell(0).setCellValue(advertisement.link());
            row.createCell(1).setCellValue(advertisement.pfCount());
            row.createCell(2).setCellValue(1);
            row.createCell(3).setCellValue(advertisement.startDate().toString());
            row.createCell(4).setCellValue(advertisement.endDate().toString());
            row.createCell(5).setCellValue(advertisement.enableContact() ? "+" : "");
            row.createCell(6).setCellValue("Цена за сутки");
            index[0]++;
        });

        return workbook;
    }
}
