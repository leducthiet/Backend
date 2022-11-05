package com.DoAnTotNghiep.api.paypal;

import javax.servlet.http.HttpServletRequest;

import com.DoAnTotNghiep.config.paypal.PaypalPaymentIntent;
import com.DoAnTotNghiep.config.paypal.PaypalPaymentMethod;
import com.DoAnTotNghiep.core.paypal.PaypalService;
import com.DoAnTotNghiep.core.tour.domain.BookingState;
import com.DoAnTotNghiep.core.tour.entity.TourBooking;
import com.DoAnTotNghiep.core.tour.service.TourBookingService;
import com.DoAnTotNghiep.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
public class PaymentController {

    public static final String URL_PAYPAL_SUCCESS = "pay/success";

    public static final String URL_PAYPAL_CANCEL = "pay/cancel";

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PaypalService paypalService;

    @Autowired
    TourBookingService tourBookingService;

    @GetMapping("/demo")
    public String index(){
        return "demo";
    }

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
                tourBooking.setBookingState(BookingState.COMPLETED);
                tourBookingService.updateTourBooking(tourBooking);

                return "success";
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }
}
