package com.chenzhiwu.beggar.system.ctrl;

import com.chenzhiwu.beggar.common.exception.ResultException;
import com.chenzhiwu.beggar.common.utils.ResultVoUtil;
import com.chenzhiwu.beggar.common.vo.ResultVo;
import com.chenzhiwu.beggar.component.fileUpload.FileUpload;
import com.chenzhiwu.beggar.component.fileUpload.enums.UploadResultEnum;
import com.chenzhiwu.beggar.modules.system.domain.Upload;
import com.chenzhiwu.beggar.modules.system.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 小懒虫
 * @date 2018/11/02
 */
@Controller
public class UploadCtrl {

    @Autowired
    private UploadService uploadService;

    /**
     * 上传web格式图片
     */
    @PostMapping("/upload/image")
    @ResponseBody
    public ResultVo uploadImage(@RequestParam("image") MultipartFile multipartFile) {

        // 创建Upload实体对象
        Upload upload = FileUpload.getFile(multipartFile, "/images");
        try {
            ResultVo resultVo = saveImage(multipartFile, upload);
            Upload resultUpload = (Upload)resultVo.getData();
            System.out.println(resultUpload.getPath());
            return resultVo;
        } catch (IOException | NoSuchAlgorithmException e) {
            return ResultVoUtil.error("上传图片失败");
        }
    }

    /**
     * 上传web格式头像
     */
    @PostMapping("/upload/picture")
    @ResponseBody
    public ResultVo uploadPicture(@RequestParam("picture") MultipartFile multipartFile) {

        // 创建Upload实体对象
        Upload upload = FileUpload.getFile(multipartFile, "/picture");
        try {
            ResultVo resultVo = saveImage(multipartFile, upload);
            System.out.println(upload.getPath());
            return resultVo;
        } catch (IOException | NoSuchAlgorithmException e) {
            return ResultVoUtil.error("上传头像失败");
        }
    }

    /**
     * 保存上传的web格式图片
     */
    private ResultVo saveImage(MultipartFile multipartFile, Upload upload) throws IOException, NoSuchAlgorithmException {
        // 判断是否为支持的图片格式
        String[] types = {
                "image/gif",
                "image/jpg",
                "image/jpeg",
                "image/png"
        };
        if(!FileUpload.isContentType(multipartFile, types)){
            throw new ResultException(UploadResultEnum.NO_FILE_TYPE);
        }

        // 判断图片是否存在
        Upload uploadSha1 = uploadService.getBySha1(FileUpload.getFileSHA1(multipartFile));
        if (uploadSha1 != null) {
            return ResultVoUtil.success(uploadSha1);
        }

        FileUpload.transferTo(multipartFile, upload);
        // 将文件信息保存到数据库中
        uploadService.save(upload);
        return ResultVoUtil.success(upload);
    }

    @PostMapping("/layUITextarea/upload")
    @ResponseBody
    public Object upload(MultipartFile file) {

        // 创建Upload实体对象
        Upload upload = FileUpload.getFile(file, "/images");
        try {

            ResultVo resultVo = saveImage(file, upload);
            Upload resultUpload = (Upload)resultVo.getData();
            System.out.println(resultUpload.getPath());
            Map<String,Object> map = new HashMap<String,Object>();
            Map<String,Object> map2 = new HashMap<String,Object>();
            map.put("code", 0);	//0表示上传成功
            map.put("msg","上传成功"); //提示消息
            //src返回图片上传成功后的下载路径
            map2.put("src", resultUpload.getPath());
            map2.put("title", resultUpload.getName());
            map.put("data", map2);
            return map;
        } catch (IOException | NoSuchAlgorithmException e) {
            return ResultVoUtil.error("上传图片失败");
        }

    }


}
