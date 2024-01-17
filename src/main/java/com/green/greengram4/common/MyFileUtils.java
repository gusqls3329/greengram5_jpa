package com.green.greengram4.common;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
        foldel.mkdirs(); //foldel.mkdir();사용하지 말기!
        return foldel.getAbsolutePath();//AbsolutePath():절대주소 : pull(전체)주소 <> 상대주소 : 기준으로부터 폴더 위/아래로 가야함
    }

    //랜덤파일명 만들기 : 파일명을 랜덤으로 바꿔서 저장 : 같은 이름이 들어오지 않도록
    public String getRandomFileNm() {
        return UUID.randomUUID().toString();
    }

    //확장자 얻어오기
    public String getExt(String fileNm) {
        String extension = null;
        if (!fileNm.equals("")) {
            extension = fileNm.substring(fileNm.lastIndexOf(".")); //substring:자르기 , lastIndexOf: 해당하는 문자열이(여러개가 있으면 마지막) 몇번째에 있는지
        }
        return extension;
    }

    //랜덤파일명 만들기 with확장자
    public String getRandomFileNm(String originFileNm) {

        String fileName = getRandomFileNm();
        String ex = getExt(originFileNm);
        return fileName + ex;
    }

    //랜덤파일명 만들기 with확장자 from MultipatrFile  : 컨트롤러에서 MultipatrFile를 가져와서
    public String getRandomFileNm(MultipartFile mf) {
        String fileName = mf.getOriginalFilename(); // 파일 정보가 문자로 오면 사진정보의 이름을 가져옴
        String Random = getRandomFileNm(fileName);

        return Random;
    }

    //메모리에 있는 내용을 파일로 옮기는 메소드 : 사진을 저장하고 파일이름 랜덤파일명으로 바꿈
    public String transferTo(MultipartFile mf, String target) {
        String fileNm = getRandomFileNm(mf); // 파일이름을 랜덤으로 바꿈(같은이름을 넣을경우를 위해서) : 파일명만 바뀌며 확장자는 그대로 살림 : 파일은 건드리지 않음
        String folderPath = makeFolders(target); //내가 저장하고싶은곳에 폴더를 만듦 : 폴더 경로
        File saveFile = new File(folderPath, fileNm); // 폴더명, 파일명 까지 되어있는 파일 객체만 만들고 파일은 없음
        saveFile.exists(); //파일(saveFile)이 존재하는지 확인 : 존재하면 ture //존재하지 않을 수 있음 :?>??
        try {
            mf.transferTo(saveFile); //saveFile 파일 객체를 옮겨줌 = 메모리에 있던 내용을 파일로 옮겨줌, 경로는 파일 객체로 보내야함.
            return fileNm; //데이터베이스에 저장을 해야하기때문에 랜덤한 파일명을 return해야함
            // 경로는 저장하지 않고 유추해서 알수있도록..? > /category/pk/filenm 으로 파일 경로를 만들기 때문에 유추가능함
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delFolderTrigger(String relativePayh) {
        delFolder(uploadStartPath + relativePayh);
    }

    public void delFolder(String folderPath) { //최종경로에 있는 폴더 안에 폴더 및 파일을 삭제
        //폴더삭제가 아닌 폴더아래의 폴더 및 파일 삭제
        File folder = new File( folderPath); //폴더 경로를 만들어줌

        if (folder.exists()) { //folfer이 존재하는지 확인
            File[] files = folder.listFiles();

            for (File file : files) {
                if (file.isDirectory()) {//isDirectory : 폴더가 있는지 확인
                    delFolder(file.getAbsolutePath());
                } else {
                    file.delete();
                }
            }
            folder.delete();
            /*"D:/download/greengram4/user/1/folder/folder/사진들"
            1 : "D:/download/greengram4/user/1" 이호출
            2. file = 디렉토리 : 아직안끝나서 1번이 한번더 호출 = "D:/download/greengram4/user/1/folder"
            3. 아직 안끝나서 2번이 한번더 호출 = "D:/download/greengram4/user/1/folder/folder"
            4. for문이 도는데 사진들만 있음 :폴더 가 없어서 사진들이 들어감 : 파일들이 삭제됨 > for문이 끝남
            5. 3번으로 다시 올라감 > 폴더가 삭제됨(폴더안에 사진이 없으면 삭제)
            6. 2번으로 다시 올러감 > 폴더가 삭제됨
            7. 1번으로 올라감 ... > 사진들이 삭제됨
             */
        }
    }
}
