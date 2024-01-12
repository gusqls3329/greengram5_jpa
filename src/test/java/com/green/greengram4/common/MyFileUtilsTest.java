package com.green.greengram4.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@Import({MyFileUtils.class})
@TestPropertySource(properties = {
        "file.dir=D:/home/download"
})
public class MyFileUtilsTest {
    @Autowired
    private MyFileUtils myFileUtils;
    @Test
    public void makeFolderTest(){
        String path = "/ggg";
        File preFolder = new File(myFileUtils.getUploadStartPath(),path); //File:io할수있음 : 컴퓨터에 있는 파일들을 컨드롤 할 수 있음
        //new File 객체를 만들 때myFileUtils.getUploadStartPath() : 첫번째 경로, (), 콤마뒤는 두번째 경로
        assertEquals(false, preFolder.exists());
        //끝에 ggg라는 폴더가 존재하는지 확인
        String newPath = myFileUtils.makeFolders(path); //makeFolders하면서 폴더를 만듦
        File newFolder = new File(newPath); // 해당 경로로 파일객체를 만들어봄(진짜로 폴더가 만들어졌는지 보기위해)
        assertEquals(preFolder.getAbsolutePath(),newFolder.getAbsolutePath());// 기존경로와 새로만들어본 객체랑 경로가 같은지 체크
        assertEquals(true, newFolder.exists());
    }

    //빈문자열이 아는지 확인
    @Test
    public void getRandomFileNmTest(){
        String fileNm = myFileUtils.getRandomFileNm();
        System.out.println("fileNm: " + fileNm);
        assertNotNull(fileNm);
        assertNotEquals("", fileNm);
    }


    @Test
    public void getExtTest(){
        String fileNm = "abc.efg.eee.jpg";
        String ext = myFileUtils.getExt(fileNm);
        assertEquals(".jpg",ext);

        String fileNm2 = "jjj-asdfasdf.pnge";
        String ext2 = myFileUtils.getExt(fileNm2);
        assertEquals(".pnge", ext2);
    }

    @Test
    public void getRandomFileNm2(){
        String fileNm1 = "안녕.친구야.jpg";
        String rfileNm1 = myFileUtils.getRandomFileNm(fileNm1);
        System.out.println("rfileNm1:{}"+rfileNm1);

        String fileNm2 = "zmzmzm.친구야.jpg";
        String rfileNm2 = myFileUtils.getRandomFileNm(fileNm2);
        System.out.println("rfileNm2:{}"+rfileNm2);

    }
}
