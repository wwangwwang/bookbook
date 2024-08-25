package com.project.bookbook.service.impl;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;
import com.project.bookbook.service.VisionService;

import lombok.RequiredArgsConstructor;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisionServiceProcess implements VisionService{
	
	private final ChatClient chatClient;
	
	/**
     * 이미지에서 텍스트를 추출하고, 그 텍스트에서 책 제목을 추출
     * 
     * @param imageStream 텍스트를 추출할 이미지의 InputStream
     * @return 추출된 책 제목을 반환
     * @throws Exception 이미지 처리 중 오류가 발생할 경우 예외를 던짐
     */
	@Override
    public String extractTextFromImage(InputStream imageStream) throws Exception {
        ByteString imgBytes = ByteString.readFrom(imageStream);

        Image img = Image.newBuilder().setContent(imgBytes).build(); //vision API에 전달할 image 객체 생성
        Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request = AnnotateImageRequest.newBuilder() //이미지 주석 요청을 생성
                .addFeatures(feat)
                .setImage(img)
                .build();
        List<AnnotateImageRequest> requests = new ArrayList<>();
        requests.add(request);
        
        // Vision API 클라이언트를 사용해 요청을 처리
        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests); //이미지 주석 응답을 배치로 받아옴
            StringBuilder stringBuilder = new StringBuilder();
            for (AnnotateImageResponse res : response.getResponsesList()) {
                if (res.hasError()) {
                    System.out.printf("Error: %s\n", res.getError().getMessage());
                    return "Error detected";
                }
                stringBuilder.append(res.getFullTextAnnotation().getText()); // 전체 텍스트 주석을 문자열에 추가
            }
            
            // 추출한 텍스트에서 책 제목만 추출하도록 ChatClient를 호출
            System.out.println("전체 텍스트 : "+stringBuilder.toString());
            String bookName = chatClient.prompt()
            		.system("결과를 '정의란 무엇인가' 처럼 딱 단어로만 말해줘.")
            		.user(stringBuilder.toString()+" >> 이 텍스트 내용을 가지고 책 검색을 할 예정이야. 책 이름을 찾으면 책 이름 하나만 return, 책 이름을 잘 모르겠으면 책 검색에 필요한 키워드를 하나만 return.").call().content();
            System.out.println("chat gpt : " + bookName);
            return bookName;
        }
    }
}