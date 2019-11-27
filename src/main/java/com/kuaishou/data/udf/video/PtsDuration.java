package com.kuaishou.data.udf.video;

import java.util.ArrayList;
import java.util.Base64;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;


public class PtsDuration extends UDF {

    public Long evaluate(String str) throws UDFArgumentException {

        if (str == null || str.length() == 0) {
            throw new UDFArgumentException("must take two arguments");
        }

        ArrayList<Long> ptsList = new ArrayList<>();
        if (str.contains(",")) {
            for (String s : str.split(",")) {
                try {
                    ptsList.add(Long.parseLong(s));
                } catch (NumberFormatException e) {
                    return 0L;
                }
            }
        } else {
            byte[] bytes = null;
            try {
                bytes = Base64.getDecoder().decode(str);
            } catch (IllegalArgumentException e) {
                return 0L;
            }
            long s = 0;
            for (int i = 0; i < bytes.length; i += 2) {
                s += (bytes[i] << 8) | (bytes[i + 1] & 0xff);
                ptsList.add(s);
            }
        }
        long dur = 0;
        for (int i = 1; i < ptsList.size(); i += 1) {
            dur = dur + ptsList.get(i) - ptsList.get(i - 1);
        }
        return dur;
    }
}