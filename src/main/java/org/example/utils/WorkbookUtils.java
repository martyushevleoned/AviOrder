package org.example.utils;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.model.dto.AdvertisementDto;
import org.example.model.dto.OrderDto;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.util.Comparator;

/**
 * Компонент для сборки .xlsx документа
 */
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

    /**
     * Собрать {@link Workbook} по {@link OrderDto}
     */
    public Workbook generateOrderWorkbook(OrderDto orderDto) {

        Workbook workbook = new XSSFWorkbook();
        CreationHelper creationHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet(String.valueOf(orderDto.orderId()));

        CellStyle centerStyle = workbook.createCellStyle();
        centerStyle.setAlignment(HorizontalAlignment.CENTER);

        CellStyle rightStyle = workbook.createCellStyle();
        rightStyle.setAlignment(HorizontalAlignment.RIGHT);

        {
            Row columns = sheet.createRow(0);
            {
                sheet.setColumnWidth(LINK_COLUMN, LINK_CELL_WIDTH);
                Cell cell = columns.createCell(LINK_COLUMN);
                cell.setCellValue("Ссылка");
            }
            {
                sheet.setColumnWidth(PF_COLUMN, NUMBER_CELL_WIDTH);
                Cell cell = columns.createCell(PF_COLUMN);
                cell.setCellValue("ПФ");
                cell.setCellStyle(centerStyle);
            }
            {
                sheet.setColumnWidth(COUNT_OF_DAYS_COLUMN, NUMBER_CELL_WIDTH);
                Cell cell = columns.createCell(COUNT_OF_DAYS_COLUMN);
                cell.setCellValue("Кол-во дней");
                cell.setCellStyle(centerStyle);
            }
            {
                sheet.setColumnWidth(START_DATE_COLUMN, DATE_CELL_WIDTH);
                Cell cell = columns.createCell(START_DATE_COLUMN);
                cell.setCellValue("Дата-Н");
                cell.setCellStyle(centerStyle);
            }
            {
                sheet.setColumnWidth(END_DATE_COLUMN, DATE_CELL_WIDTH);
                Cell cell = columns.createCell(END_DATE_COLUMN);
                cell.setCellValue("Дата-К");
                cell.setCellStyle(centerStyle);
            }
            {
                sheet.setColumnWidth(CONTACTS_COLUMN, NUMBER_CELL_WIDTH);
                Cell cell = columns.createCell(CONTACTS_COLUMN);
                cell.setCellValue("Контакты");
                cell.setCellStyle(centerStyle);
            }
            {
                sheet.setColumnWidth(PRICE_COLUMN, NUMBER_CELL_WIDTH);
                Cell cell = columns.createCell(PRICE_COLUMN);
                cell.setCellValue("Цена за период");
                cell.setCellStyle(rightStyle);
            }
        }

        {
            final long[] summaryCost = {0};
            final int[] index = {1};
            orderDto.advertisementDtoList().stream().sorted(Comparator.comparing(AdvertisementDto::endDate)).forEach(advertisementDto -> {
                long countOfDays = ChronoUnit.DAYS.between(advertisementDto.startDate(), advertisementDto.endDate()) + 1;
                Row row = sheet.createRow(index[0]);

                { //ссылка
                    Cell cell = row.createCell(LINK_COLUMN);
                    cell.setCellValue(advertisementDto.link());
                    Hyperlink hyperlink = creationHelper.createHyperlink(HyperlinkType.URL);
                    hyperlink.setAddress(advertisementDto.link());
                    cell.setHyperlink(hyperlink);
                }
                { // количество ПФ
                    Cell cell = row.createCell(PF_COLUMN);
                    cell.setCellValue(advertisementDto.pfCount());
                    cell.setCellStyle(centerStyle);
                }
                { // количество дней
                    Cell cell = row.createCell(COUNT_OF_DAYS_COLUMN);
                    cell.setCellValue(countOfDays);
                    cell.setCellStyle(centerStyle);
                }
                { // дата начала
                    Cell cell = row.createCell(START_DATE_COLUMN);
                    cell.setCellValue(advertisementDto.startDate().toString());
                    cell.setCellStyle(centerStyle);
                }
                { // дата окончания
                    Cell cell = row.createCell(END_DATE_COLUMN);
                    cell.setCellValue(advertisementDto.endDate().toString());
                    cell.setCellStyle(centerStyle);
                }
                { // контакты
                    if (advertisementDto.enableContact()) {
                        Cell cell = row.createCell(CONTACTS_COLUMN);
                        cell.setCellValue("+");
                        cell.setCellStyle(centerStyle);
                    }
                }
                { // стоимость
                    long cost = advertisementDto.pfCount() * 4 * countOfDays;
                    row.createCell(PRICE_COLUMN).setCellValue(cost);
                    summaryCost[0] += cost;
                }

                index[0]++;
            });

            Row row = sheet.createRow(index[0]);
            {
                Cell cell = row.createCell(PRICE_COLUMN - 1);
                cell.setCellValue("Итого");
                cell.setCellStyle(rightStyle);
            }
            {
                row.createCell(PRICE_COLUMN).setCellValue(summaryCost[0]);
            }
        }

        return workbook;
    }
}
