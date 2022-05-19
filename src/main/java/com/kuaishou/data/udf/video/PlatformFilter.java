package com.kuaishou.data.udf.video;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.hadoop.hive.ql.exec.UDF;

import com.kuaishou.kconf.client.Kconf;
import com.kuaishou.kconf.client.Kconfs;

/**
 * @author <zhouwenjia@kuaishou.com>
 * Created on 2022-01-11
 */
public class PlatformFilter extends UDF {
    private static final Kconf<Set<String>> platformFilter = Kconfs.ofStringSet("videoData.whiteList.platformFilter", new HashSet<>()).build();
    public boolean evaluate(String platform) {
        Set<String> set = platformFilter.get();
        String s = platform.toUpperCase(Locale.ROOT);
        return set.contains(s);
    }
}