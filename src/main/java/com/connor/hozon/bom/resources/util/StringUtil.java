package com.connor.hozon.bom.resources.util;

import java.text.Collator;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by haozt on 2018/5/22
 */
public class StringUtil {

    public static boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

}
