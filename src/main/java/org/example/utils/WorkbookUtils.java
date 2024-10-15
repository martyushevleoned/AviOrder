package org.example.utils;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.model.dto.OrderDto;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.util.Comparator;

@Component
public class WorkbookUtils {

    private static final int LINK_COLUMN = 0;
    private static final int PF_COLUMN = 1;
    private static final int COUNT_OF_DAYS_COLUMN = 2;
    private static final int START_DATE_COLUMN = 3;
    private static final int END_DATE_COLUMN = 4;
    private static final int CONTACTS_COLUMN = 5;
    private static final int PRICE_COLUMN = 6;

    private static final int LINK_CELL_WIDTH = 20000;
    private static final int NUMBER_CELL_WIDTH = 4500;
    private static final int DATE_CELL_WIDTH = 4000;

    public Workbook generateWorkbook(OrderDto orderDto) {

        Workbook workbook = new XSSFWorkbook();
        CreationHelper creationHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet(orderDto.name());

        CellStyle leftStyle = workbook.createCellStyle();
        leftStyle.setAlignment(HorizontalAlignment.LEFT);

        CellStyle centerStyle = workbook.createCellStyle();
        centerStyle.setAlignment(HorizontalAlignment.CENTER);

        CellStyle rightStyle = workbook.createCellStyle();
        rightStyle.setAlignment(HorizontalAlignment.RIGHT);

        { // названия колонок
            Row header = sheet.createRow(0);

            sheet.setColumnWidth(LINK_COLUMN, LINK_CELL_WIDTH);
            createCell(header, LINK_COLUMN, "Ссылка", leftStyle);

            sheet.setColumnWidth(PF_COLUMN, NUMBER_CELL_WIDTH);
            createCell(header, PF_COLUMN, "ПФ", centerStyle);

            sheet.setColumnWidth(COUNT_OF_DAYS_COLUMN, NUMBER_CELL_WIDTH);
            createCell(header, COUNT_OF_DAYS_COLUMN, "Количество дней", centerStyle);

            sheet.setColumnWidth(START_DATE_COLUMN, DATE_CELL_WIDTH);
            createCell(header, START_DATE_COLUMN, "Дата-Н", centerStyle);

            sheet.setColumnWidth(END_DATE_COLUMN, DATE_CELL_WIDTH);
            createCell(header, END_DATE_COLUMN, "Дата-K", centerStyle);

            sheet.setColumnWidth(CONTACTS_COLUMN, NUMBER_CELL_WIDTH);
            createCell(header, CONTACTS_COLUMN, "Контакты", centerStyle);

            sheet.setColumnWidth(PRICE_COLUMN, NUMBER_CELL_WIDTH);
            createCell(header, PRICE_COLUMN, "Цена за период", rightStyle);
        }

        // содержимое колонок
        final long[] summaryCost = {0};
        final int[] index = {1};
        orderDto.advertisements().stream()
                .sorted(Comparator.comparing(OrderDto.AdvertisementDto::promotionEndDate))
                .forEach(advertisementDto -> {

                    Row row = sheet.createRow(index[0]);
                    long cost = getCost(advertisementDto);

                    Hyperlink hyperlink = creationHelper.createHyperlink(HyperlinkType.URL);
                    hyperlink.setAddress(advertisementDto.link());
                    createCell(row, LINK_COLUMN, advertisementDto.link(), leftStyle).setHyperlink(hyperlink);

                    createCell(row, PF_COLUMN, advertisementDto.productFactorCountPerDay(), centerStyle);
                    createCell(row, COUNT_OF_DAYS_COLUMN, getCountOfDays(advertisementDto), centerStyle);
                    createCell(row, START_DATE_COLUMN, advertisementDto.promotionStartDate(), centerStyle);
                    createCell(row, END_DATE_COLUMN, advertisementDto.promotionEndDate(), centerStyle);
                    if (advertisementDto.promoteContacts())
                        createCell(row, CONTACTS_COLUMN, "+", centerStyle);
                    createCell(row, PRICE_COLUMN, cost, rightStyle);

                    summaryCost[0] += cost;
                    index[0]++;
                });

        { // итоговая стоимость
            Row row = sheet.createRow(index[0]);
            createCell(row, PRICE_COLUMN - 1, "Итоговая стоимость:", rightStyle);
            createCell(row, PRICE_COLUMN, summaryCost[0], rightStyle);
        }

        return workbook;
    }

    private Cell createCell(Row row, int column, Object value, CellStyle cellStyle) {
        Cell cell = row.createCell(column);
        cell.setCellValue(String.valueOf(value));
        cell.setCellStyle(cellStyle);
        return cell;
    }

    private long getCountOfDays(OrderDto.AdvertisementDto advertisementDto) {
        return ChronoUnit.DAYS.between(advertisementDto.promotionStartDate(), advertisementDto.promotionEndDate()) + 1;
    }

    private long getCost(OrderDto.AdvertisementDto advertisementDto) {
        return advertisementDto.productFactorCountPerDay() * getCountOfDays(advertisementDto) * 4;
    }
}
