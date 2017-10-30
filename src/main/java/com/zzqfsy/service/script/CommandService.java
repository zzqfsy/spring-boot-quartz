//package com.zzq.service.script;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//
///**
// * Created by zzqana on 8/23/2016.
// */
//@Service
//public class CommandService {
//    private static Logger log = LoggerFactory.getLogger(CommandService.class);
//
//    public String excuteCmd(String cmd){
//        StringBuilder result = new StringBuilder();
//        BufferedReader br = null;
//        try {
//            Process p = Runtime.getRuntime().exec(cmd);
//            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            String lineStr;
//            while ((lineStr = br.readLine()) != null) {
//                result.append(lineStr);
//            }
//            br.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        finally {
//            if (br != null){
//                try {
//                    br.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return result.toString();
//    }
//}
