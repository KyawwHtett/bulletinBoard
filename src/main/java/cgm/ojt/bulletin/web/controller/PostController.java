package cgm.ojt.bulletin.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cgm.ojt.bulletin.bl.dto.CategoryDto;
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
	public ModelAndView createPostConfirm(@ModelAttribute("postForm") @Valid PostForm postForm, BindingResult result) {
		ModelAndView mv = new ModelAndView("createPost");
//		System.out.println(postForm.getPost_categories());
//		System.out.println(postForm.getCategory());
		List<Category> categoryList = this.categoryService.doGetAllCategory();
		mv.addObject("categoryList", categoryList);
		if (result.hasErrors()) {
			return mv;
		}
		var resultLists = new ArrayList<Category>();
		for (String categoryId : postForm.getCategory()) {
			System.out.println(categoryId + "gggggg");
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

	@RequestMapping(value = "/post/save", method = RequestMethod.POST)
	public ModelAndView savePost(@ModelAttribute("postForm") PostDto postDto) {
		ModelAndView mv = new ModelAndView("redirect:/post/list");
		for (String categoryId : postDto.getCategory()) {
			categoryId = categoryId.replaceAll("[^\\d.]", "");
			System.out.println(categoryId+"hello 31");
			if (Integer.parseInt(categoryId) == 2) {
				System.out.println("hello 3333333333333");
			}
			System.out.println(categoryId);
		}
		this.postService.doSavePost(postDto);
		return mv;
	}
	
	@RequestMapping(value = "/post/list", method = RequestMethod.GET)
	public ModelAndView postList() {
		ModelAndView listPost = new ModelAndView("listPost");
		List<PostDto> postDto = this.postService.doGetAllPost();
		listPost.addObject("listPost", postDto);
		return listPost;
	}

//	public <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
//		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
//		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
//			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
//				return (o1.getValue()).compareTo(o2.getValue());
//			}
//		});
//
//		Map<K, V> result = new LinkedHashMap<K, V>();
//		for (Map.Entry<K, V> entry : list) {
//			result.put(entry.getKey(), entry.getValue());
//		}
//		return result;
//	}
}