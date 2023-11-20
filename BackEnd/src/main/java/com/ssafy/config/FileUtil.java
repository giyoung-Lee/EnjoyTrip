package com.ssafy.config;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.board.model.BoardDto;
import com.ssafy.file.model.FileDto;

public class FileUtil {
	
	// 게시판 이미지 저장 경로(ref: application.properties)
    @Value("${file.path.board-images}")
    private String uploadImagePath;
    
    // 이미지 저장 및 dto변환
    public FileDto storeImg(MultipartFile multipartFile, BoardDto boardDto) throws IOException {
        if(multipartFile.isEmpty()) {
            return null;
        }
        
        String today = new SimpleDateFormat("yyMMdd").format(new Date());
        String saveFolder = uploadImagePath + File.separator + today;
        File folder = new File(saveFolder);
        if (!folder.exists())
            folder.mkdirs();
        
        FileDto imgInfoDto = new FileDto();
        String originalFileName = multipartFile.getOriginalFilename();
        if (!originalFileName.isEmpty()) {
            String saveFileName = UUID.randomUUID().toString()
                    + originalFileName.substring(originalFileName.lastIndexOf('.'));
            imgInfoDto.setSaveFolder(today);
            imgInfoDto.setOriginalFile(originalFileName);
            imgInfoDto.setSaveFile(saveFileName);
            imgInfoDto.setArticle_no(boardDto.getArticle_no());
            imgInfoDto.setUser_id(boardDto.getUser_id());
            // 파일 실제 저장
            multipartFile.transferTo(new File(folder, saveFileName));
        }
        
        return imgInfoDto;
    }
    
    // 이미지 저장 및 dto변환
    public List<FileDto> storeImgs(List<MultipartFile> multipartFiles, BoardDto boardDto) throws IOException {
        List<FileDto> fileInfos = new ArrayList<FileDto>();
        for(MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()) {
                fileInfos.add(storeImg(multipartFile, boardDto));
            }
        }
        
        return fileInfos;
    }
}
