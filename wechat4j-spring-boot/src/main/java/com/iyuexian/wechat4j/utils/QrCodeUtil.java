package com.iyuexian.wechat4j.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 二维码工具类
 * 
 * @author WeChat4j Team
 */
@Component
public class QrCodeUtil {

    private static final Logger logger = LoggerFactory.getLogger(QrCodeUtil.class);

    /**
     * 保存二维码图片
     * 
     * @param imageData 图片数据
     * @param filePath 文件路径
     */
    public void saveQrCodeImage(byte[] imageData, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(imageData);
            logger.info("二维码保存成功: {}", filePath);
        } catch (IOException e) {
            logger.error("保存二维码失败: {}", filePath, e);
            throw e;
        }
    }

    /**
     * 生成模拟二维码数据（用于测试）
     */
    public byte[] generateMockQrCodeData(String content) {
        // 简单的模拟数据，实际应使用ZXing等库生成真正的二维码
        return content.getBytes();
    }
}