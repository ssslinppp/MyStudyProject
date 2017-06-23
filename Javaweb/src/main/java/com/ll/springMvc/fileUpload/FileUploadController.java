package com.ll.springMvc.fileUpload;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 参考：http://www.cnblogs.com/ssslinppp/p/4607043.html
 * 
 * @author LINLIU-Serv
 * Date: 2017-5-27
 * Time: 下午01:52:41
 */
@Controller
@RequestMapping("/filesUpload")
public class FileUploadController {

	@RequestMapping(value = "uploadPage")
	public String uploadPage() {
		return "uploadPage";
	}

	/**
	 * 上传单个文件
	 * 
	 * @param name
	 * @param file
	 *            上传的文件自动保存到MultipartFile中
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadFile")
	public String updateThumb(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request, ModelMap model) throws Exception {
		if (!file.isEmpty()) {
			// 保存文件-方式1 --测试过，可以用,必须先创建相应目录
			// file.transferTo(new File("d:/"+file.getOriginalFilename()));

			// 保存文件-方式2
			String path = request.getSession().getServletContext()
					.getRealPath("upload");
			String fileName = file.getOriginalFilename();
			File targetFile = new File(path, fileName);
			System.out.println("文件上传路径：" + targetFile.getAbsolutePath());
			// 目录不存在，则创建目录
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			// 保存
			try {
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			model.addAttribute("fileUrl", request.getContextPath() + "/upload/"
					+ fileName);
			return "uploadFileSuccess";
		} else {
			return "uploadFileFail";
		}
	}
}
