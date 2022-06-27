package cgm.ojt.bulletin.web.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cgm.ojt.bulletin.bl.dto.PostDto;
import cgm.ojt.bulletin.bl.service.CategoryService;
import cgm.ojt.bulletin.bl.service.PostService;
import cgm.ojt.bulletin.persistence.entity.Category;
import cgm.ojt.bulletin.web.form.PostForm;

@Controller
public class PostController {
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PostService postService;

	@RequestMapping(value = "/post/create", method = RequestMethod.GET)
	public ModelAndView createPost() {
		ModelAndView mv = new ModelAndView("createPost");
		PostForm postForm = new PostForm();
		Map<Integer, String> categoryListMap = new HashMap<Integer, String>();
		List<Category> categoryList = this.categoryService.doGetAllCategory();

		for (Category category : categoryList) {
			categoryListMap.put(category.getCategory_id(), category.getCategory_name());
		}
		mv.addObject("postForm", postForm);
		mv.addObject("categoryList", categoryList);
		mv.addObject("categoryListMap", categoryListMap);
		return mv;
	}

	@RequestMapping(value = "/post/createConfirm", method = RequestMethod.POST)
	public ModelAndView createPostConfirm(@ModelAttribute("postForm") @Valid PostForm postForm, BindingResult result,
			@RequestParam("imageData") String imageData) {
		ModelAndView mv = new ModelAndView("createPost");
		if (imageData.length() > 0) {
			postForm.setPost_img(imageData);
		}
		List<Category> categoryList = this.categoryService.doGetAllCategory();
		mv.addObject("categoryList", categoryList);
		if (result.hasErrors()) {
			return mv;
		}
		var resultLists = new ArrayList<Category>();
		for (String categoryId : postForm.getCategory()) {
			for (Category category : categoryList) {
				if (Integer.parseInt(categoryId) == category.getCategory_id()) {
					resultLists.add(category);
				}
			}
		}
		mv.addObject("resultLists", resultLists);
		mv.addObject("postForm", postForm);
		mv.setViewName("createPostConfirm");
		return mv;
	}

	@RequestMapping(value = "/post/createConfirm", params = "back", method = RequestMethod.POST)
	public ModelAndView cancelCreatePost() {
		return new ModelAndView("redirect:/post/list");
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/post/save", method = RequestMethod.POST)
	public ModelAndView savePost(@ModelAttribute("postForm") PostDto postDto, HttpServletRequest request)
			throws FileNotFoundException, IOException {
		ModelAndView mv = new ModelAndView("redirect:/post/list");
		String postImgPath = request.getRealPath("/") + "/resources/images/" + postDto.getTitle();
		Path uploadPath = Paths.get(postImgPath);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		postImgPath = uploadPath + "/" + postDto.getTitle() + ".png";
		this.postService.doSavePost(postDto, postImgPath);
		return mv;
		
	}

	@RequestMapping(value = "/post/save", params = "back", method = RequestMethod.POST)
	public ModelAndView cancelSavePost(@ModelAttribute("postForm") PostForm postForm) {
		ModelAndView cancelSavePost = new ModelAndView("createPost");
		List<Category> listCategory = this.categoryService.doGetAllCategory();

		ArrayList<String> list = new ArrayList<String>();
		for (String categoryId : postForm.getCategory()) {
			categoryId = categoryId.replaceAll("[^\\d.]", "");
			list.add(categoryId);
		}
		String[] newCategory = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			newCategory[i] = list.get(i);
		}
		postForm.setCategory(newCategory);
		cancelSavePost.addObject("categoryList", listCategory);
		cancelSavePost.addObject("postForm", postForm);

		return cancelSavePost;
	}

	@RequestMapping(value = "/post/list", method = RequestMethod.GET)
	public ModelAndView postList(HttpServletRequest request, PostForm postForm) throws IOException {
		ModelAndView listPost = new ModelAndView("listPost");
		int currentPage = getCurrentPage(request);
		int recordsPerPage = getRecordsPerPage(request);
		this.getPagination(listPost, currentPage, recordsPerPage, false, postForm);
		return listPost;
	}

	@RequestMapping(value = "/post/search", method = RequestMethod.GET)
	public ModelAndView getSearchPost(HttpServletRequest request) throws IOException {
		ModelAndView listPost = new ModelAndView("listPost");
		PostForm postForm = new PostForm();
		int currentPage = getCurrentPage(request);
		int recordsPerPage = getRecordsPerPage(request);
		String postSearch = request.getParameter("post_search");
		if ((postSearch == null) || (postSearch.isEmpty())) {
			this.getPagination(listPost, currentPage, recordsPerPage, false, postForm);
		} else {
			postForm.setPost_search(postSearch.trim());
			this.getPagination(listPost, currentPage, recordsPerPage, true, postForm);
			listPost.addObject("searchData", postSearch);
		}
		return listPost;
	}

	@RequestMapping(value = "/post/search", method = RequestMethod.POST)
	public ModelAndView searchPost(@RequestParam("post_search") String postSearch, HttpServletRequest request)
			throws IOException {
		ModelAndView listPost = new ModelAndView("listPost");
		PostForm postForm = new PostForm();
		int currentPage = getCurrentPage(request);
		int recordsPerPage = getRecordsPerPage(request);
		if (postSearch.isEmpty()) {
			this.getPagination(listPost, currentPage, recordsPerPage, false, postForm);
		} else {
			postForm.setPost_search(postSearch.trim());
			this.getPagination(listPost, currentPage, recordsPerPage, true, postForm);
			listPost.addObject("searchData", postSearch);
		}
		return listPost;
	}

	@RequestMapping(value = "/post/download", method = RequestMethod.GET)
	public ModelAndView downloadAllPost(HttpServletResponse response) throws IOException {
		this.postService.doDownloadAllPost(response);
		return null;
	}

	@RequestMapping(value = "/post/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editPost(@PathVariable("id") Integer postId) throws IOException {
		ModelAndView mv = new ModelAndView("editPost");
		List<Category> categoryList = this.categoryService.doGetAllCategory();
		PostDto post = this.postService.doGetPostById(postId);
		ArrayList<String> list = new ArrayList<String>();
		for (Category category : post.getPost_categories()) {
			list.add(Integer.toString(category.getCategory_id()));
		}
		String[] newCategory = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			newCategory[i] = list.get(i);
		}
		post.setCategory(newCategory);
		mv.addObject("categoryList", categoryList);
		mv.addObject("postForm", post);
		return mv;
	}

	@RequestMapping(value = "/post/editConfirm", params = "back", method = RequestMethod.POST)
	public ModelAndView cancelConfirmEditPost() {
		return new ModelAndView("redirect:/post/list");
	}

	@RequestMapping(value = "/post/editConfirm", method = RequestMethod.POST)
	public ModelAndView confirmEditPost(@ModelAttribute("postForm") @Valid PostForm postForm, BindingResult result,
			@RequestParam("imageData") String imageData) {
		ModelAndView mv = new ModelAndView("editPost");
		List<Category> categoryList = this.categoryService.doGetAllCategory();
		List<Category> resultLists = new ArrayList<Category>();
		if (imageData.length() > 0) {
			postForm.setPost_img(imageData);
		}
		ArrayList<String> firstList = new ArrayList<String>();
		ArrayList<String> secondList = new ArrayList<String>();
		for (Category category : categoryList) {
			for (String stringArray : postForm.getCategory()) {
				if (Integer.parseInt(stringArray) == category.getCategory_id()) {
					resultLists.add(category);
					firstList.add(category.getCategory_name());
					;
				}
			}
			secondList.add(category.getCategory_name());
		}
		secondList.removeAll(firstList);
		List<Category> remainCategory = new ArrayList<Category>();
		for (String categoryName : secondList) {
			for (Category category : categoryList) {
				if (category.getCategory_name() == categoryName) {
					remainCategory.add(category);
				}
			}
		}
		mv.addObject("resultLists", resultLists);
		mv.addObject("remainCategory", remainCategory);

		mv.addObject("categoryList", categoryList);
		if (result.hasErrors()) {
			System.out.println("errrors");
			return mv;
		}
		mv.setViewName("editPostConfirm");
		mv.addObject("postForm", postForm);
		return mv;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/post/update", method = RequestMethod.POST)
	public ModelAndView updatePost(@ModelAttribute("postForm") PostDto postDto, HttpServletRequest request)
			throws IOException {
		ModelAndView mv = new ModelAndView("redirect:/post/list");
		Path path = Paths.get(request.getRealPath("/") + "/resources/images/" + postDto.getTitle());
		String postImgPath = Files.createDirectories(path) + "/" + postDto.getTitle() + ".png";
		this.postService.doUpdatePost(postDto, postImgPath);
		return mv;
	}

	@RequestMapping(value = "/post/update", params = "back", method = RequestMethod.POST)
	public ModelAndView updatePost(@ModelAttribute("postForm") PostForm postForm) {
		ModelAndView backEditPost = new ModelAndView("editPost");
		List<Category> listCategory = this.categoryService.doGetAllCategory();

		ArrayList<String> list = new ArrayList<String>();
		for (String categoryId : postForm.getCategory()) {
			categoryId = categoryId.replaceAll("[^\\d.]", "");
			list.add(categoryId);
		}
		String[] newCategory = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			newCategory[i] = list.get(i);
		}
		postForm.setCategory(newCategory);
		backEditPost.addObject("postForm", postForm);
		backEditPost.addObject("categoryList", listCategory);
		return backEditPost;
	}

	private int getCurrentPage(HttpServletRequest request) {
		int currentPage = request.getParameter("currentPage") != null
				? Integer.valueOf(request.getParameter("currentPage"))
				: 1;
		return currentPage;
	}

	private int getRecordsPerPage(HttpServletRequest request) {
		int recordsPerPage = request.getParameter("recordsPerPage") != null
				? Integer.valueOf(request.getParameter("recordsPerPage"))
				: 8;
		return recordsPerPage;
	}

	private void getPagination(ModelAndView listPost, int currentPage, int recordsPerPage, boolean searchResult,
			PostForm postForm) throws IOException {

		List<PostDto> postList = null;
		if (searchResult == false) {
			postList = this.postService.doGetAllPost();
		} else {
			postList = this.postService.doGetAllPostBySearchInput(postForm.getPost_search());
		}
		int rows = postList.size();
		System.out.println();
		int nOfPages = rows / recordsPerPage;
		if (nOfPages % recordsPerPage > 0) {
			nOfPages++;
		}
		List<PostDto> resultPostList = this.postService.doGetAllSearchPost(currentPage, recordsPerPage, postForm);
		listPost.addObject("noOfPages", nOfPages);
		listPost.addObject("currentPage", currentPage);
		listPost.addObject("recordsPerPage", recordsPerPage);
		listPost.addObject("listPost", resultPostList);
	}
}