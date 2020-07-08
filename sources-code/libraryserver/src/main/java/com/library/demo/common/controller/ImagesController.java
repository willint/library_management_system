package com.library.demo.common.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.library.demo.core.utils.TableIdGeneratorUtils;
import com.library.demo.core.vo.RespVo;

@RestController
@RequestMapping("images")
public class ImagesController {

  private Logger logger = LoggerFactory.getLogger(ImagesController.class);

  @Value("${libsys.imagesUploadFolder}")
  private String IMAGES_UPLOAD_FOLDER;

  @RequestMapping("/upload")
  public RespVo imageUpload(MultipartFile file, String type) {
    try {
      String filename = file.getOriginalFilename();
      // 获取后缀
      String prefix = filename.substring(filename.lastIndexOf(".") + 1);
      
      // 修改后完整的文件名称
      filename = TableIdGeneratorUtils.getId() + "." + prefix;
      byte[] bytes = file.getBytes();
      // 获取上传的文件的名称
      Path path =
          Paths.get(IMAGES_UPLOAD_FOLDER + File.separator + type + File.separator + filename);
      // 如果没有files文件夹，则创建
      if (!Files.isWritable(path)) {
        Files.createDirectories(Paths.get(IMAGES_UPLOAD_FOLDER + File.separator + type));
      }
      // 文件写入指定路径
      Files.write(path, bytes);
      Map<String, String> body = new HashMap<>();
      body.put("imageUrl", File.separator + type + File.separator + filename);
      return new RespVo(body);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return new RespVo("-1", "上传失败:" + e.getMessage());
    }

  }
}
