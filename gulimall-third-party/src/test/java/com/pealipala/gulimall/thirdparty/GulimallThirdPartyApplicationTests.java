package com.pealipala.gulimall.thirdparty;

import com.aliyun.oss.OSS;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
class GulimallThirdPartyApplicationTests {

    @Autowired
    OSS ossClient;

    @Test
    void contextLoads() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("C:\\Users\\pealipala\\Downloads\\1591286266(1).png");
        ossClient.putObject("gulimall-pealipala","TEST1.png",inputStream);
        ossClient.shutdown();
        System.out.println("1");
    }

}
