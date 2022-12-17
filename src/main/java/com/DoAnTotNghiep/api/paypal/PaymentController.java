package com.DoAnTotNghiep.api.paypal;

import javax.servlet.http.HttpServletRequest;

import com.DoAnTotNghiep.config.paypal.PaypalPaymentIntent;
import com.DoAnTotNghiep.config.paypal.PaypalPaymentMethod;
import com.DoAnTotNghiep.core.paypal.PayoutService;
import com.DoAnTotNghiep.core.paypal.PaypalService;
import com.DoAnTotNghiep.core.tour.domain.BookingState;
import com.DoAnTotNghiep.core.tour.entity.Invoice;
import com.DoAnTotNghiep.core.tour.entity.TourBooking;
import com.DoAnTotNghiep.core.tour.entity.TravelAgency;
import com.DoAnTotNghiep.core.tour.service.InvoiceService;
import com.DoAnTotNghiep.core.tour.service.ProductService;
import com.DoAnTotNghiep.core.tour.service.TourBookingService;
import com.DoAnTotNghiep.core.tour.service.TravelAgencyService;
import com.DoAnTotNghiep.utils.Utils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

@Controller
public class PaymentController {

    public static final String URL_PAYPAL_SUCCESS = "pay/success";

    public static final String URL_PAYPAL_CANCEL = "pay/cancel";

    public static final String URL_PAYPAL_SUCCESS_PAY_SERVICE = "payService/success";

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PaypalService paypalService;

    @Autowired
    TourBookingService tourBookingService;

    @Autowired
    TravelAgencyService travelAgencyService;

    @Autowired
    ProductService productService;

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    PayoutService payoutService;

    @PostMapping("/pay")
    public String pay(HttpServletRequest request,
                      @RequestParam("price") double price,
                      @RequestParam("tourBookingId") Long tourBookingId){
        String cancelUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_CANCEL;
        String successUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_SUCCESS;

        try {
            Payment payment = paypalService.createPayment(
                    price,
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "payment description",
                    cancelUrl,
                    successUrl);

            TourBooking tourBooking = tourBookingService.findById(tourBookingId);
            tourBooking.setPaymentId(payment.getId());
            tourBookingService.updateTourBooking(tourBooking);

            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return "redirect:" + links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping(URL_PAYPAL_CANCEL)
    public String cancelPay(){
        return "cancel";
    }

    @GetMapping(URL_PAYPAL_SUCCESS)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if(payment.getState().equals("approved")){

                TourBooking tourBooking = tourBookingService.getTourBookingByPaymentId(paymentId);
                TravelAgency travelAgency = tourBooking.getTourDateBooking().getTour().getTravelAgency();

                String accessToken = payoutService.getAccessToken();
                Long senderBatchId = tourBookingService.getMaxSenderBatchId();
                if (accessToken != null) {
                    String payoutBatchId = payoutService.createPayout(accessToken, travelAgency.getPaypalId(),
                            senderBatchId, tourBooking.getTotalPriceUSD().substring(0, 4));
                    if (payoutBatchId != null) {
                        tourBooking.setPayoutBatchId(payoutBatchId);
                        tourBooking.setSenderBatchId(senderBatchId + 1);
                    }
                }

                tourBooking.setBookingState(BookingState.COMPLETED);
                tourBookingService.updateTourBooking(tourBooking);

                return "success";
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/payService")
    public String payService(HttpServletRequest request,
                      @RequestParam("month") Integer month,
                      @RequestParam("travelAgencyId") Long travelAgencyId) throws IOException {
        String cancelUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_CANCEL;
        String successUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_SUCCESS_PAY_SERVICE;

        double price = month * Double.parseDouble(exchange(productService.findById(travelAgencyService.findById(travelAgencyId).getProduct().getId()).getPrice()));

        try {
            Payment payment = paypalService.createPayment(
                    price,
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "payment description",
                    cancelUrl,
                    successUrl);

            TravelAgency travelAgency = travelAgencyService.findById(travelAgencyId);
            travelAgency.setPaymentId(payment.getId());
            travelAgency.setMonth(month);
            travelAgencyService.updateTravelAgency(travelAgency);

            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return "redirect:" + links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping(URL_PAYPAL_SUCCESS_PAY_SERVICE)
    public String successPayService(Model model, @RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if(payment.getState().equals("approved")){

                TravelAgency travelAgency = travelAgencyService.getTravelAgencyByPaymentId(paymentId);
                Date expiredDateBefore = travelAgency.getExpiredDate();
                Calendar c = Calendar.getInstance();
                c.setTime(expiredDateBefore);
                c.add(Calendar.DATE, 30 * travelAgency.getMonth());
                Date expiredDateAfter = c.getTime();
                travelAgency.setExpiredDate(expiredDateAfter);
                travelAgencyService.updateTravelAgency(travelAgency);

                Invoice invoice = new Invoice();
                invoice.setMonth(travelAgency.getMonth());
                invoice.setCreatedDate(new Date());
                invoice.setPrice(travelAgency.getMonth() * travelAgency.getProduct().getPrice());
                invoice.setTravelAgency(travelAgencyService.findById(travelAgency.getId()));
                invoiceService.createInvoice(invoice);

                model.addAttribute("travelAgency", travelAgencyService.findById(travelAgency.getId()));

                return "managerPaymentSuccess";
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }

    String exchange(Long priceVND) throws IOException {
        String url_str = "https://api.exchangerate.host/latest?base=VND&symbols=USD&amount=" + priceVND;

        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonObj = root.getAsJsonObject();

        JsonObject jsonObjRate = jsonObj.get("rates").getAsJsonObject();

        return jsonObjRate.get("USD").getAsString();
    }
}
