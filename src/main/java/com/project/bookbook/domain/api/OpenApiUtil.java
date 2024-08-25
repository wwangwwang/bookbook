package com.project.bookbook.domain.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class OpenApiUtil {
	
	// HTTP 요청을 보내고 응답을 받는 메서드
	public String request(String apiUrl, Map<String, String> requestHeaders, String methodType, String requestBody) {
		HttpURLConnection con = connect(apiUrl);
		try {
			con.setRequestMethod(methodType);
			// 요청 헤더 설정
			if (requestHeaders != null) {
				for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
					con.setRequestProperty(header.getKey(), header.getValue());
				}
			}
			
			// POST 일때 필요시 requestBody 입력을 JSON
			if (requestBody != null) {
				con.setDoOutput(true);
				try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
					wr.write(requestBody.getBytes());
					wr.flush();
				}
			}
			
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
				// json 데이터 읽기처리
				return readBody(con.getInputStream());
			} else { // 오류 발생
				return readBody(con.getErrorStream());
			}
		} catch (IOException e) {
			throw new RuntimeException("API 요청과 응답 실패", e);
		} finally {
			con.disconnect();
		}
	}
	
	// URL 연결을 생성하는 private 메서드
	private HttpURLConnection connect(String apiUrl) {
		try {
			URL url = new URL(apiUrl);
			return (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
		} catch (IOException e) {
			throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
		}
	}

	// 입력 스트림에서 응답 본문을 읽는 private 메서드	
	private String readBody(InputStream body) {
		InputStreamReader streamReader = new InputStreamReader(body);
		try (BufferedReader lineReader = new BufferedReader(streamReader)) {
			StringBuilder responseBody = new StringBuilder();

			String line;
			while ((line = lineReader.readLine()) != null) {
				responseBody.append(line);
			}
			return responseBody.toString();
		} catch (IOException e) {
			throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
		}
	}

	public String requestForRedirect(String apiUrl, Map<String, String> requestHeaders, String methodType,
			String requestBody) throws IOException {
		HttpURLConnection con = connect(apiUrl);
		try {
			con.setRequestMethod(methodType);

			for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}

			con.setInstanceFollowRedirects(false); // 자동 리다이렉트를 비활성화

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP || responseCode == HttpURLConnection.HTTP_MOVED_PERM
					|| responseCode == HttpURLConnection.HTTP_SEE_OTHER) {
				String newUrl = con.getHeaderField("Location");
				return newUrl;
			} else if (responseCode == HttpURLConnection.HTTP_OK) {
				// 일부 API는 리다이렉트 대신 직접 다운로드 URL을 반환할 수 있습니다.
				return readBody(con.getInputStream());
			} else {
				throw new RuntimeException("Unexpected response code: " + responseCode);
			}
		} finally {
			con.disconnect();
		}
	}
	// 바이너리 데이터를 다운로드하는 메서드
	public byte[] requestBinary(String apiUrl, Map<String, String> requestHeaders, String methodType,
			String requestBody) throws IOException {
		HttpURLConnection con = connect(apiUrl);
		try {
			con.setRequestMethod(methodType);

			for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}

			// 연결 타임아웃 및 읽기 타임아웃 설정
			con.setConnectTimeout(30000); // 30초
			con.setReadTimeout(30000); // 30초

			int responseCode = con.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				// 정상 응답일 경우 바이너리 데이터 읽기
				try (InputStream is = con.getInputStream()) {
					byte[] data = is.readAllBytes();
					return data;
				}
			} else {
				String errorResponse = readErrorStream(con);
				throw new RuntimeException(
						"Failed to download file. Response code: " + responseCode + ", Error: " + errorResponse);
			}
		} finally {
			con.disconnect();
		}
	}
	
	// 에러 스트림을 읽는 private 메서드
	private String readErrorStream(HttpURLConnection con) throws IOException {
		InputStream errorStream = con.getErrorStream();
		if (errorStream == null) {
			return "No error message available";
		}
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream, StandardCharsets.UTF_8))) {
			StringBuilder errorMessage = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				errorMessage.append(line);
			}
			return errorMessage.toString();
		}
	}

	/**
	 * 
	 * @param <T>
	 * @param responseJSONData : 객체로 변환할 JSON 형식의 문자열 data
	 * @param typeReference    : 변환할 객체를 TypeReference<T> 타입에 정의
	 * @return
	 * @throws Exception
	 */
	public <T> T objectMapper(String responseJSONData, TypeReference<T> typeReference) throws Exception {
		return new ObjectMapper().readValue(responseJSONData, typeReference);
	}

}
