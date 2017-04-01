package com.mobile.library.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 
 * @author lihy
 *
 */
public class StringUtil {

	/**
	 * 格式化Double
	 * 
	 * @param pattern
	 *            格式 as ###0.00
	 * @param d
	 *            数值
	 * @return 格式化字符串
	 */
	public static String formatDouble(String pattern, double d) {
		DecimalFormat decimalFormat = new DecimalFormat(pattern);// 格式化设置
		return decimalFormat.format(d);

	}

	/**
	 * 格式化Double
	 * 
	 * @param d
	 *            数值
	 * @return 格式化字符串
	 */
	public static String formatDouble(double d) {
		return formatDouble("###0.00", d);
	}

	/**
	 * 格式化FLoat
	 * 
	 * @param pattern
	 *            格式 as ###0.00
	 * @param f
	 *            数值
	 * @return 格式化字符串
	 */
	public static String formatFLoat(String pattern, float f) {
		DecimalFormat decimalFormat = new DecimalFormat(pattern);// 格式化设置
		return decimalFormat.format(f);

	}

	/**
	 * 格式化FLoat
	 * 
	 * @param f
	 *            数值
	 * @return 格式化字符串
	 */
	public static String formatFLoat(float f) {
		return formatFLoat("###0.00", f);

	}

	/**
	 * 将网络文件转换成本地文件名
	 * @param url
	 * @return
     */
	public static String getSdFileName(String url) {
		return url.replace("/", "").replace(":", "").replace("?", "");
	}

	/**
	 * 剔除字符串尾的特定字符
	 * 
	 * @param str
	 * @param trimChar
	 * @return
	 */
	public static String trimEnd(String str, char trimChar) {
		if (StringUtil.isNullOrEmpty(str))
			return str;
		char[] charArray = str.toCharArray();
		int index = -1;
		for (int i = charArray.length; i >= 0; i--) {
			if (charArray[i - 1] != trimChar) {
				index = i;
				break;
			}
		}
		if (index < 0)
			return str;
		return String.valueOf(charArray, 0, index);
	}

	/**
	 * 剔除字符串头上的特定字符
	 * 
	 * @param str
	 * @param trimChar
	 * @return
	 */
	public static String trimStart(String str, char trimChar) {
		if (StringUtil.isNullOrEmpty(str))
			return str;
		char[] charArray = str.toCharArray();
		int index = -1;
		for (int i = 0; i < charArray.length; i++) {
			if (charArray[i] != trimChar) {
				index = i;
				break;
			}
		}
		if (index < 0)
			return str;
		return String.valueOf(charArray, index, charArray.length - index);
	}

	/**
	 * 是否是Null或者为空
	 * 
	 * @param str
	 * @return
	 */
	@Deprecated
	public static boolean isNullOrEmpty(String str) {
		return (str == null || str.equals(""));
	}

	/**
	 * 判断字符串是否为null或长度为0
	 *
	 * @param s
	 *            待校验字符串
	 * @return {@code true}: 空<br>
	 *         {@code false}: 不为空
	 */
	public static boolean isEmpty(CharSequence s) {
		return s == null || s.length() == 0;
	}

	/**
	 * 判断字符串是否为null或全为空格
	 *
	 * @param s
	 *            待校验字符串
	 * @return {@code true}: null或全空格<br>
	 *         {@code false}: 不为null且不全空格
	 */
	public static boolean isSpace(String s) {
		return (s == null || s.trim().length() == 0);
	}

	/**
	 * 判断两字符串是否相等
	 *
	 * @param a
	 *            待校验字符串a
	 * @param b
	 *            待校验字符串b
	 * @return {@code true}: 相等<br>
	 *         {@code false}: 不相等
	 */
	public static boolean equals(CharSequence a, CharSequence b) {
		if (a == b)
			return true;
		int length;
		if (a != null && b != null && (length = a.length()) == b.length()) {
			if (a instanceof String && b instanceof String) {
				return a.equals(b);
			} else {
				for (int i = 0; i < length; i++) {
					if (a.charAt(i) != b.charAt(i))
						return false;
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断两字符串忽略大小写是否相等
	 *
	 * @param a
	 *            待校验字符串a
	 * @param b
	 *            待校验字符串b
	 * @return {@code true}: 相等<br>
	 *         {@code false}: 不相等
	 */
	public static boolean equalsIgnoreCase(String a, String b) {
		return (a == b) || (b != null) && (a.length() == b.length()) && a.regionMatches(true, 0, b, 0, b.length());
	}

	/**
	 * 返回字符串长度
	 *
	 * @param s
	 *            字符串
	 * @return null返回0，其他返回自身长度
	 */
	public static int length(CharSequence s) {
		return s == null ? 0 : s.length();
	}

	/**
	 * 处理 Null 字符串
	 * 
	 * @return
	 */
	public static String checkNull(String str) {
		return checkNull(str, "");
	}

	/**
	 * 处理 Null 字符串,返回默认字段
	 * 
	 * @param str
	 *            数据
	 * @param defaultStr
	 *            默认展示
	 * @return
	 */
	public static String checkNull(String str, String defaultStr) {
		if (isNullOrEmpty(str)) {
			return defaultStr;
		}
		return str;
	}

	/**
	 * 是否为Null或者为空
	 * 
	 * @param sb
	 *            被判定的字符串
	 * @return 是否为Null或者空
	 */
	public static boolean isNullOrEmpty(StringBuffer sb) {
		return sb == null || isNullOrEmpty(sb.toString());
	}

	/**
	 * 连接字符串
	 * 
	 * @param con
	 *            连接符
	 * @param items
	 *            连接的对象
	 * @return
	 */
	public static String join(String con, List<String> items) {
		if (items.size() == 0)
			return "";

		if (items.size() == 1)
			return items.get(0);

		StringBuffer sb = new StringBuffer(items.get(0));
		for (int i = 1; i < items.size(); i++) {
			sb.append(con + items.get(i));
		}
		return sb.toString();
	}

	/**
	 * arg0是否是以arg1开头的
	 * 
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public static boolean isStartWith(String arg0, String arg1) {
		if (isNullOrEmpty(arg1) || isNullOrEmpty(arg0))
			return false;

		if (arg1.length() >= arg0.length())
			return false;

		String str = arg0.substring(0, arg1.length());
		return arg1.equals(str);
	}

	/**
	 * arg0是否是以arg1结尾的
	 * 
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public static boolean isEndWith(String arg0, String arg1) {
		if (isNullOrEmpty(arg1) || isNullOrEmpty(arg0))
			return false;

		if (arg1.length() > arg0.length())
			return false;

		String str = arg0.substring(arg0.length() - arg1.length());
		return arg1.equals(str);
	}

	/**
	 * 首字母大写
	 * 
	 * @param s
	 * @return
	 */
	public static String firstLetterToUpperCase(String s) {
		if (isEmpty(s) || !Character.isLowerCase(s.charAt(0)))
			return s;
		return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
	}

	/**
	 * 首字母小写
	 * 
	 * @param s
	 * @return
	 */
	public static String firstLetterToLowerCase(String s) {
		if (isEmpty(s) || !Character.isUpperCase(s.charAt(0)))
			return s;
		return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
	}

	/**
	 * 获取字符串长度（区分单双字节）
	 * 
	 * @param s
	 * @return
	 */
	public static int getLength(CharSequence s) {
		int length = 0;
		for (int i = 0; i < s.length(); i++) {
			int ascii = Character.codePointAt(s, i);
			if (ascii >= 0 && ascii <= 255)
				length++;
			else
				length += 2;
		}
		return length / 2 + (length % 2 == 0 ? 0 : 1);
	}

	/**
	 * 反转字符串
	 *
	 * @param s
	 *            待反转字符串
	 * @return 反转字符串
	 */
	public static String reverse(String s) {
		int len = length(s);
		if (len <= 1)
			return s;
		int mid = len >> 1;
		char[] chars = s.toCharArray();
		char c;
		for (int i = 0; i < mid; ++i) {
			c = chars[i];
			chars[i] = chars[len - i - 1];
			chars[len - i - 1] = c;
		}
		return new String(chars);
	}

	/**
	 * 转化为半角字符
	 *
	 * @param s
	 *            待转字符串
	 * @return 半角字符串
	 */
	public static String toDBC(String s) {
		if (isEmpty(s))
			return s;
		char[] chars = s.toCharArray();
		for (int i = 0, len = chars.length; i < len; i++) {
			if (chars[i] == 12288) {
				chars[i] = ' ';
			} else if (65281 <= chars[i] && chars[i] <= 65374) {
				chars[i] = (char) (chars[i] - 65248);
			} else {
				chars[i] = chars[i];
			}
		}
		return new String(chars);
	}

	/**
	 * 转化为全角字符
	 *
	 * @param s
	 *            待转字符串
	 * @return 全角字符串
	 */
	public static String toSBC(String s) {
		if (isEmpty(s))
			return s;
		char[] chars = s.toCharArray();
		for (int i = 0, len = chars.length; i < len; i++) {
			if (chars[i] == ' ') {
				chars[i] = (char) 12288;
			} else if (33 <= chars[i] && chars[i] <= 126) {
				chars[i] = (char) (chars[i] + 65248);
			} else {
				chars[i] = chars[i];
			}
		}
		return new String(chars);
	}

	/**
	 * 是否为手机号
	 * @param str 字符串
	 * @return 是否符合
     */
	public static boolean isPhoneNo(String str) {
		return str.matches(ConstUtils.REGEX_MOBILE_SIMPLE);
	}

	/**
	 * 是否为手机号(精确)
	 * @param str 字符串
	 * @return 是否符合
     */
	public static boolean isPhoneNoExact(String str) {
		return str.matches(ConstUtils.REGEX_MOBILE_EXACT);
	}

	/**
	 * 是否为数字
	 * @param str 字符串
	 * @return 是否符合
     */
	public static boolean isNumber(String str) {
		return str.matches("^\\d+$");
	}

	/**
	 *  是否为email
	 * @param str 字符串
	 * @return 是否符合
     */
	public static boolean isEmail(String str) {
		return str.matches(
				"^[a-z A-Z 0-9 _]+@[a-z A-Z 0-9 _]+(\\.[a-z A-Z 0-9 _]+)+(\\,[a-z A-Z 0-9 _]+@[a-z A-Z 0-9 _]+(\\.[a-z A-Z 0-9 _]+)+)*$");
	}

	/**
	 * 是否整个字符串为网址
	 * @param str 字符串
	 * @return 是否符合
     */
	public static boolean isWebSiteAll(String str) {
		return str.matches("http[s]?://[a-zA-z0-9]+.([a-zA-z0-9]+)+");
	}

	/**
	 * 是否字符串中包含网址
	 * @param str 字符串
	 * @return 是否符合
     */
	public static boolean isWebSitePart(String str) {
		String patternString = "http[s]?://[a-zA-z0-9]+.([a-zA-z0-9]+)+";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(str);

		return matcher.find();
	}

	/**
	 * 数组拼接
	 * @param first 列表1
	 * @param second 列表2
	 * @param <T> 类型
     * @return 拼接后的列表
     */
	public static <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	/**
	 * 转换网络路径
	 * 
	 * @param s 字符串
	 * @return 无中文的 Url
	 */
	public static String toURLString(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				try {
					sb.append(URLEncoder.encode(String.valueOf(c), "utf-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

}
