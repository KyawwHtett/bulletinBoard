package cgm.ojt.bulletin.bl.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.util.IOUtils;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cgm.ojt.bulletin.bl.dto.PostDto;
import cgm.ojt.bulletin.bl.dto.UserDto;
import cgm.ojt.bulletin.bl.service.PostService;
import cgm.ojt.bulletin.persistence.dao.CategoryDao;
import cgm.ojt.bulletin.persistence.dao.PostDao;
import cgm.ojt.bulletin.persistence.entity.Category;
import cgm.ojt.bulletin.persistence.entity.Post;
import cgm.ojt.bulletin.web.form.PostForm;

@Service
@Transactional
public class PostServiceImpl implements PostService {
	@Autowired
	private PostDao postDao;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private HttpSession session;

	@Override
	public void doSavePost(PostDto postDto, String postImgPath) throws IOException {
		String imageBase64 = postDto.getPost_img();
		if (!imageBase64.isEmpty() && !imageBase64.equals("") && !imageBase64.equals(null)) {
			String[] block = imageBase64.split(",");
			String realData = block[1];
			byte[] data = Base64.decodeBase64(realData);
			try (FileOutputStream stream = new FileOutputStream(postImgPath)) {
				stream.write(data);
			}
			postDto.setPost_img(postImgPath);
		}
		this.postDao.dbSavePost(this.getPostObject(postDto));
	}

	@Override
	public List<PostDto> doGetAllPost() throws IOException {
		List<Post> listPost = this.postDao.dbGetAllPost();
		List<PostDto> postDtoList = new ArrayList<PostDto>();
		for (Post post : listPost) {
			PostDto postDto = new PostDto();
			postDto.setPost_id(post.getPost_id());
			postDto.setTitle(post.getTitle());
			postDto.setDescription(post.getDescription());
			postDto.setPost_categories(post.getPost_categories());
			postDto.setDate(prettyTimeFormat(post.getUpdated_at()));
			postDto.setPost_img(post.getPost_img());
			postDtoList.add(postDto);
		}
		return postDtoList;
	}

	@Override
	public List<PostDto> doGetAllPostBySearchInput(String post_search) {
		List<Post> listPost = this.postDao.dbGetAllPostBySearchInput(post_search);
		List<PostDto> postDtoList = new ArrayList<PostDto>();
		for (Post post : listPost) {
			PostDto postDto = new PostDto();
			postDto.setPost_id(post.getPost_id());
			postDto.setTitle(post.getTitle());
			postDto.setDescription(post.getDescription());
			postDto.setPost_categories(post.getPost_categories());
			postDto.setDate(prettyTimeFormat(post.getUpdated_at()));
			postDto.setPost_img(post.getPost_img());
			postDtoList.add(postDto);
		}
		return postDtoList;
	}

	@Override
	public void doDownloadAllPost(HttpServletResponse response) throws IOException {
		List<Post> listPost = this.postDao.dbGetAllPost();
		String fileName = "post_list.xls";

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Post List");
		HSSFRow rowHead = sheet.createRow((short) 0);
		rowHead.createCell(0).setCellValue("ID");
		rowHead.createCell(1).setCellValue("Post ID");
		rowHead.createCell(2).setCellValue("Title");
		rowHead.createCell(3).setCellValue("Description");
		rowHead.createCell(4).setCellValue("Category Name");
		rowHead.createCell(5).setCellValue("Created_at");

		int count = 1;
		for (Post post : listPost) {
			HSSFRow row = sheet.createRow((short) count);
			row.createCell(0).setCellValue(count);
			row.createCell(1).setCellValue(post.getPost_id());
			row.createCell(2).setCellValue(post.getTitle());
			row.createCell(3).setCellValue(post.getDescription());
			ArrayList<String> list = new ArrayList<String>();
			for (Category category : post.getPost_categories()) {
				list.add(category.getCategory_name());
			}
			String categoryList = list.toString();
			categoryList = categoryList.replaceAll("\\[", "").replaceAll("\\]", "");
			row.createCell(4).setCellValue(categoryList);
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			row.createCell(5).setCellValue(dateformat.format(post.getCreated_at()));
			count++;
		}
		count = 0;

		try {
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", fileName);
			response.setHeader(headerKey, headerValue);
			workbook.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workbook.close();
			IOUtils.closeQuietly(response.getOutputStream());
		}
	}

	@SuppressWarnings("resource")
	@Override
	public PostDto doGetPostById(Integer postId) throws IOException {
		Post post = this.postDao.dbGetPostById(postId);
		PostDto postDto = new PostDto(post);

		if (postDto.getPost_img() != null) {
			String postImagePath = postDto.getPost_img();
			File postImgFile = new File(postImagePath);
			postDto.setPost_img(null);
			if (postImgFile.exists()) {
				FileInputStream fis = new FileInputStream(postImgFile);
				byte byteArray[] = new byte[(int) postImgFile.length()];
				fis.read(byteArray);
				String imageString = "data:image/png;base64," + Base64.encodeBase64String(byteArray);
				postDto.setPost_img(imageString);
			}
		}
		return postDto;
	}

	@Override
	public void doUpdatePost(PostDto postDto, String postImgPath) throws IOException {
		this.postDao.dbUpdatePost(this.getPostUpdate(postDto, postImgPath));
	}

	@SuppressWarnings("resource")
	@Override
	public List<PostDto> doGetAllSearchPost(int currentPage, int recordsPerPage, PostForm postForm) throws IOException {
		List<PostDto> resultPostList = new ArrayList<PostDto>();
		List<Post> postList = this.postDao.dbGetAllSearchPost(currentPage, recordsPerPage, postForm);
		for (Post post : postList) {
			PostDto newPostDto = new PostDto(post);
			if (newPostDto.getPost_img() != null) {
				String postImgPath = newPostDto.getPost_img();
				File postImgFile = new File(postImgPath);
				newPostDto.setPost_img(null);
				if (postImgFile.exists()) {
					FileInputStream fis = new FileInputStream(postImgFile);
					byte byteArray[] = new byte[(int) postImgFile.length()];
					fis.read(byteArray);
					String imageString = "data:image/png;base64," + Base64.encodeBase64String(byteArray);
					newPostDto.setPost_img(imageString);
				}
			}
			newPostDto.setDate(prettyTimeFormat(post.getUpdated_at()));
			resultPostList.add(newPostDto);
		}
		return resultPostList;
	}

	private String prettyTimeFormat(Date date) {
		PrettyTime pt = new PrettyTime();
		return pt.format(date);
	}

	private Post getPostObject(PostDto postDto) {
		Set<Category> categoryListMap = new HashSet<Category>();
		List<Category> categoryList = this.categoryDao.dbGetAllCategory();

		for (String categoryId : postDto.getCategory()) {
			categoryId = categoryId.replaceAll("[^\\d.]", "");
			System.out.println(categoryId);

			for (Category category : categoryList) {
				if (Integer.parseInt(categoryId) == category.getCategory_id()) {
					categoryListMap.add(category);
				}
			}
		}
		UserDto userDto = (UserDto) this.session.getAttribute("LOGIN_USER");
		Post post = new Post();
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setPost_img(postDto.getPost_img());
		post.setCreated_user_id(userDto.getId());
		post.setCreated_at(new Date());
		post.setUpdated_at(new Date());
		post.setPost_categories(categoryListMap);
		return post;
	}

	private Post getPostUpdate(PostDto postDto, String postImgPath) throws IOException {
		Set<Category> categoryListMap = new HashSet<Category>();
		List<Category> categoryList = this.categoryDao.dbGetAllCategory();

		for (String categoryId : postDto.getCategory()) {
			categoryId = categoryId.replaceAll("[^\\d.]", "");

			for (Category category : categoryList) {
				if (Integer.parseInt(categoryId) == category.getCategory_id()) {
					categoryListMap.add(category);
				}
			}
		}

		String updateImagePath = postDto.getPost_img();
		if (postImgPath.length() > 0) {
			if (updateImagePath.length() > 0 && !updateImagePath.equals(postImgPath)) {
				File deletedOldImage = new File(postImgPath);
				deletedOldImage.delete();
				String imageBase64 = postDto.getPost_img();
				String[] block = imageBase64.split(",");
				String realData = block[1];
				byte[] data = Base64.decodeBase64(realData);
				try (FileOutputStream stream = new FileOutputStream(postImgPath)) {
					stream.write(data);
				}
			}
		} else {
			postImgPath = updateImagePath;
		}
		UserDto userDto = (UserDto) session.getAttribute("LOGIN_USER");
		Post post = new Post();
		post.setPost_id(postDto.getPost_id());
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setPost_img(postImgPath);
		post.setUpdated_user_id(userDto.getId());
		post.setUpdated_at(new Date());
		post.setPost_categories(categoryListMap);
		return post;
	}
}