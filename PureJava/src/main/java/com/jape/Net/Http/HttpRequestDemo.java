package com.jape.Net.Http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HttpRequestDemo {

    public static void main(String[] args) throws IOException {
        HttpRequestDemo demo = new HttpRequestDemo();

        demo.getRequest();


    }


    private void getRequest() throws IOException {
        //根据URL地址创建一个URL对象
        URL url = new URL("https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date=2020-04-27&leftTicketDTO.from_station=BJP&leftTicketDTO.to_station=SHH&purpose_codes=ADULT");

        URLConnection conn = url.openConnection();
        conn.setRequestProperty("Cookie", "RAIL_EXPIRATION=1588288315090; RAIL_DEVICEID=odqzAs3hvS68iF2N2vDXclfGPzj6C1rTr536Sj9NqvHnToXRMIh5YC6Wu6LNsOboZTuzHn8PZmb4pc1s_ow1CL9lrU3G19fjLqb0D4FKaJAyokIyhiMuBeO6-d5PkCZq2DkVjuEfVws-wrh7o5IrjsC5sBm_MNyV");

        InputStreamReader in = new InputStreamReader(conn.getInputStream(),"utf-8");

        BufferedReader bfreader = new BufferedReader(in);

        StringBuffer sb = new StringBuffer();

        String line = "";

        while ((line = bfreader.readLine()) != null) {
            sb.append(line);
        }
        System.out.println(sb.toString());
    }




    private void postRequest() {

    }
}
