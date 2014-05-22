package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringUtil {

	public static String readInputStream(InputStream stream) throws IOException {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;

		br = new BufferedReader(new InputStreamReader(stream));
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}

		return sb.toString();
	}
	
	public static String capitalizeString(String input)
	{
		if(input.length() > 0){
			return input.substring(0, 1).toUpperCase() + input.substring(1);
		}

		return input;
	}

}
