package com.DoAnTotNghiep.api.image;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api")
public class ImageController {

    @GetMapping(value = "/get-image", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType(@RequestParam(name = "url", defaultValue = "") String url) {
        if (url.compareTo("null") == 0) {
            url = "src/main/resources/static/empty.jpg";
        }
        if (url.compareTo("undefined") == 0) {
            url = "src/main/resources/static/empty.jpg";
        }
        InputStream in = null;
        url = "src/main/resources/static/images/" + url;
        try {
            in = new FileInputStream(url);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
