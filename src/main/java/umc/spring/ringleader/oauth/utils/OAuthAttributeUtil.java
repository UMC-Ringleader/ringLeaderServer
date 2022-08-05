package umc.spring.ringleader.oauth.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OAuthAttributeUtil {

	public static String getEmailFromAttribute(String attribute) {
		Pattern p = Pattern.compile("([\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Za-z]{2,4})");
		Matcher m = p.matcher(attribute);

		while (m.find()) {
			if (m.group(1) != null) {
				break;
			}
		}

		return m.group(1);
	}
}
