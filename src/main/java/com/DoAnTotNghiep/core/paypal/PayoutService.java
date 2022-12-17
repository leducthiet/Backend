package com.DoAnTotNghiep.core.paypal;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

@Service
public class PayoutService {

    public String getAccessToken() {
        try {
            String url = "https://api.sandbox.paypal.com/v1/oauth2/token";
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("accept", "application/json");
            con.setRequestProperty("accept-language", "en_US");
            con.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            con.setRequestProperty("authorization", "basic QVNzNDRaenV6eGQxUGd4LUtmbGxqdGV0QTV6TTJJQ245RUZnNjlKSGs5WGZFQlI3YUI4a2JZVjBnZ2pkeTFIM3JJeDg0R0NKWmhSY3l4QVQ6RU1xMGpvbDRVMzVlZ1ZOckVlMUtvc3oxVGFkQXgyUE9kNTZvX1JfdVZ1MWNxNW11TzdvcTRlYWpyWnNPdGhnTmVYSkt1MWhLV0VwOUVLbUM=");
            String body = "grant_type=client_credentials";

            // Send request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(body);
            wr.flush();
            wr.close();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader(con.getInputStream()));
            JsonObject jsonObj = root.getAsJsonObject();

            String accessToken = jsonObj.get("access_token").getAsString();
            System.out.println(accessToken);

            return accessToken;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String createPayout(String accessToken, String paypalId, Long senderBatchId, String money) {

        try {
            String url = "https://api.sandbox.paypal.com/v1/payments/payouts";
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("accept", "application/json");
            con.setRequestProperty("authorization", "Bearer " + accessToken);
            con.setRequestProperty("content-type", "application/json");
            String body = "{" +
                    "  \"sender_batch_header\": {" +
                    "    \"email_subject\": \"You have a payment\"," +
                    "    \"sender_batch_id\": \"batch-" + senderBatchId + "\"" +
                    "  }," +
                    "  \"items\": [" +
                    "    {" +
                    "      \"recipient_type\": \"PAYPAL_ID\"," +
                    "      \"amount\": {" +
                    "        \"value\": \"" + money + "\"," +
                    "        \"currency\": \"USD\"" +
                    "      }," +
                    "      \"receiver\": \"" + paypalId +"\"," +
                    "      \"note\": \"Payouts sample transaction\"," +
                    "      \"sender_item_id\": \"item-1-1671246335604\"" +
                    "    }" +
                    "  ]" +
                    "}";

            // Send request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(body);
            wr.flush();
            wr.close();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader(con.getInputStream()));
            JsonObject jsonObj = root.getAsJsonObject();

            JsonObject batchHeader = jsonObj.get("batch_header").getAsJsonObject();
            String payoutBatchId = batchHeader.get("payout_batch_id").getAsString();

            System.out.println(payoutBatchId);

            return payoutBatchId;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
