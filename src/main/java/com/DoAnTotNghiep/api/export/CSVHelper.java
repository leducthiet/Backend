package com.DoAnTotNghiep.api.export;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import com.DoAnTotNghiep.core.tour.entity.TourBooking;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

public class CSVHelper {

    private static final String[] HEADER = {"Ngày khởi hành", "Tên khách hàng", "Số lượng người lớn",
                                            "Số lượng trẻ em 5 đến 11 tuổi", "Số lượng trẻ em dưới 5 tuổi",
                                            "Số điện thoại"};

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static ByteArrayInputStream tourBookingsToCSV(List<TourBooking> tourBookings) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            csvPrinter.printRecord(Arrays.asList(HEADER));
            for (TourBooking tourBooking : tourBookings) {
                List<String> data = Arrays.asList(
                        dateFormat.format(tourBooking.getTourDateBooking().getDateBooking()),
                        tourBooking.getCustomerName(),
                        tourBooking.getQuantityAdult().toString(),
                        tourBooking.getQuantityChild5To11().toString(),
                        tourBooking.getQuantityChild2To5().toString(),
                        tourBooking.getCustomerPhone()
                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
