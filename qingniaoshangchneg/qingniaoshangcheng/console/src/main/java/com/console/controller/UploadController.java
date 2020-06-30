package com.console.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.core.common.FastDFSClient;
import com.core.common.IDUtils;
import com.core.common.SERVER_URL;

import net.fckeditor.response.UploadResponse;

/**
 * 用来处理图片上传
 *
 * @author quan
 */

@Controller
public class UploadController {

    @ResponseBody
    @RequestMapping("/upload/uoloadImg.do")
    public void uploadImg(@RequestParam MultipartFile picfile, HttpServletRequest request, HttpServletResponse response) {
        //编写图片上传的业务逻辑方法
        //获取图片名称
        String filename = picfile.getOriginalFilename();
        //获取图片扩展名
        String ext = filename.substring(filename.lastIndexOf(".") + 1);
        //生成图片名称
        String imgName = IDUtils.genImageName();
        //生成图片的存放在服务器的路径
        String path = "/imgs/" + imgName + "." + ext;
        //获取服务器的绝对路径进行保存图片
        String url = request.getSession().getServletContext().getRealPath("") + path;

        //图片上传
        try {
            InputStream in = picfile.getInputStream();
            OutputStream out = new FileOutputStream(new File(url));
            byte[] b = new byte[1024];
            int len;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
            in.close();
            out.close();

            //把图片的路径使用json的格式进行返回
            JSONObject jo = new JSONObject();
            jo.put("path", path);
            response.getWriter().write(jo.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传图片到图片服务器
     *
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/upload/ImgtoFastDFS.do")
    public void uploadImgtoFastDFS(@RequestParam MultipartFile picfile, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FastDFSClient fastDFSClient = new FastDFSClient("classpath:properties/fastDFS.properties");
        String filename = picfile.getOriginalFilename();
        //获取图片扩展名
        String ext = filename.substring(filename.lastIndexOf(".") + 1);
        String path = fastDFSClient.uploadFile(picfile.getBytes(), ext);    //保存到数据
        String url = SERVER_URL.IMG_SERVER + path;    //显示
        JSONObject jo = new JSONObject();
        jo.put("path", path);
        jo.put("url", url);
        response.getWriter().write(jo.toString());
    }

    /**
     * fck图片上传
     *
     * @throws Exception
     */

    @RequestMapping("/upload/productDesc.do")
    public void productDesc(HttpServletRequest request, HttpServletResponse response) throws Exception {
        FastDFSClient fastDFSClient = new FastDFSClient("classpath:properties/fastDFS.properties");
        //把request包装成MultipartRequest
        MultipartRequest multipartRequest = (MultipartRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        //遍历map拿到文件对象
        Set<Entry<String, MultipartFile>> fileSet = fileMap.entrySet();
        for (Entry<String, MultipartFile> file : fileSet) {
            MultipartFile pic = file.getValue(); //得到了文件对象
            String picAddr = fastDFSClient.uploadFile(pic.getBytes(), FilenameUtils.getExtension(pic.getName()));
            String url = SERVER_URL.IMG_SERVER + picAddr;

            //把地址写回到浏览器
            UploadResponse ok = UploadResponse.getOK(url);
            //把对象写给浏览器
            response.getWriter().print(ok);
        }

    }
}
