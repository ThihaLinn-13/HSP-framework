package com.hover.project.exception.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

public class ExceptionUtil {

    public static String getStackTraceAsString(Throwable ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        pw.print("Application Trace:");

        Arrays.stream(ex.getStackTrace())
                .filter(element -> element.getClassName().startsWith("com.hover.attendance"))
                .limit(3)
                .forEach(element -> pw.print("  ▶ " + element ));

        return sw.toString();
    }

}
