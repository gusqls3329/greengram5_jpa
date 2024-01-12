package com.green.greengram4.common;

import com.green.greengram4.feed.model.FeedInsDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Getter
@Component
public class MyFileUtils {
    private final String uploadStartPath;


    public MyFileUtils(@Value("${file.dir}") String uploadStartPath) {
        this.uploadStartPath = uploadStartPath;
    }

    //폴더 만들기
    public String makeFolders(String path) {
        File foldel = new File(uploadStartPath, path);
        foldel.mkdirs();
        return foldel.getAbsolutePath();//AbsolutePath():절대주소 : pull(전체)주소 <> 상대주소 : 기준으로부터 폴더 위/아래로 가야함
    }
//랜덤파일명 만들기 : 파일명을 랜덤으로 바꿔서 저장 : 같은 이름이 들어오지 않도록
    public String getRandomFileNm(){
        return UUID.randomUUID().toString();
    }
    //확장자 얻어오기
    public String getExt(String fileNm){
        String extension = null;
        if (!fileNm.equals("")) {
            extension = fileNm.substring(fileNm.lastIndexOf(".")); //substring:자르기 , lastIndexOf: 해당하는 문자열이(여러개가 있으면 마지막) 몇번째에 있는지
        }
        return extension;
    }

    //랜덤파일명 만들기 with확장자
    public String getRandomFileNm(String originFileNm){

        String fileName = getRandomFileNm();
        String ex = getExt(fileName);
            return fileName + ex;
    }
    //랜덤파일명 만들기 with확장자 from MultipatrFile  : 컨트롤러에서 MultipatrFile를 가져와서
    public String getRandomFileNm(MultipartFile mf){
        String fileName = mf.getOriginalFilename();
        fileName = getRandomFileNm();
        String ex = getExt(fileName);

        return null;zz
    }
}
