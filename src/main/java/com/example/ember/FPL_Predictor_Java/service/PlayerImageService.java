package com.example.ember.FPL_Predictor_Java.service;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;

@Service
public class PlayerImageService {

    List<String> playerImageURLs;
    List<Integer> playerCodes;


    @Autowired
    public PlayerImageService(List<Integer> playerCodes, List<String> playerImageURLs) {
        this.playerCodes = playerCodes;
        this.playerImageURLs = playerImageURLs;
    }

    public List<String> getImageUrls(List<Integer> playerCodes){
        this.playerCodes.addAll(playerCodes);

        for(Integer code:playerCodes){
            String template = "https://resources.premierleague.com/premierleague/photos/players/110x140/p{0}.png";
            String result = MessageFormat.format(template, String.valueOf(code));
            this.playerImageURLs.add(result);
        }

        return this.playerImageURLs;
    }

//    public List<BufferedImage> getPlayerImages(List<Integer> playerCodes) throws IOException {
//        this.playerCodes.addAll(playerCodes);
//        for(Integer code:playerCodes){
//            String template = "https://resources.premierleague.com/premierleague/photos/players/110x140/p{0}.png";
//            String result = MessageFormat.format(template, code);
//            URL url = new URL(result);
//
//            ByteArrayOutputStream bao = new ByteArrayOutputStream();
//            BufferedImage img = ImageIO.read(url);
//
//            ImageIO.write(img, "png", url.getOutputStream());
//
//
//            return bao.toByteArray();
//
//        }
//
//        return playerImages;
//    }
}
