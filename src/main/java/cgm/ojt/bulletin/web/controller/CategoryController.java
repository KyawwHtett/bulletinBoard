package cgm.ojt.bulletin.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cgm.ojt.bulletin.bl.dto.CategoryDto;
import cgm.ojt.bulletin.bl.service.CategoryService;
import cgm.ojt.bulletin.persistence.entity.Category;
import cgm.ojt.bulletin.web.form.CategoryForm;

@Controller
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/category/list", method = RequestMethod.GET)
	public ModelAndView categoryList() {
		ModelAndView mv = new ModelAndView("listCategory");
		List<Category> listCategory = this.categoryService.doGetAllCategory();
		mv.addObject("listCategory", listCategory);
		return mv;
	}

	@RequestMapping(value = "/category/create", method = RequestMethod.GET)
	public ModelAndView createCategory() {
		ModelAndView mv = new ModelAndView("createCategory");
		CategoryForm categoryForm = new CategoryForm();
		mv.addObject("createCategoryForm", categoryForm);
		return mv;
	}

	@RequestMapping(value = "/category/createConfirm", method = RequestMethod.POST)
	public ModelAndView confirmCreateCateogry(@ModelAttribute("createCategoryForm") @Valid CategoryForm categoryForm,
			BindingResult result) {
		ModelAndView mv = new ModelAndView("createCategory");
		if (result.hasErrors()) {
			return mv;
		}
		mv.addObject("createCategoryForm", categoryForm);
		mv.setViewName("createCategoryConfirm");
		return mv;
	}

	@RequestMapping(value = "/category/createConfirm", params = "back", method = RequestMethod.POST)
	public ModelAndView cancelConfirmCreateCateogry() {
		return new ModelAndView("redirect:/category/list");
	}

	@RequestMapping(value = "/category/save", method = RequestMethod.POST)
	public ModelAndView saveCategory(@ModelAttribute("createCategoryForm") CategoryForm categoryForm) {
		ModelAndView mv = new ModelAndView("redirect:/category/list");
		this.categoryService.doSaveCategory(categoryForm);
		return mv;
	}

	@RequestMapping(value = "/category/edit", method = RequestMethod.GET)
	public ModelAndView editCategory(@RequestParam("id") Integer categoryId, HttpSession session) {
		ModelAndView mv = new ModelAndView("editCategory");
		CategoryDto categoryDto = this.categoryService.doGetCategoryById(categoryId);
		mv.addObject("editCategoryForm", categoryDto);
		return mv;
	}

	@RequestMapping(value = "/category/editConfirm", method = RequestMethod.POST)
	public ModelAndView editCategoryConfirm(@ModelAttribute("editCategoryForm") @Valid CategoryForm categoryForm,
			BindingResult result) {
		ModelAndView mv = new ModelAndView("editCategory");
		if (result.hasErrors()) {
			return mv;
		}
		mv.addObject("editCategoryForm", categoryForm);
		mv.setViewName("editCategoryConfirm");
		return mv;
	}
	
	@RequestMapping(value = "/category/editConfirm", params = "back", method = RequestMethod.POST)
	public ModelAndView cancelEditCategoryConfirm() {
		return new ModelAndView("redirect:/category/list");
	}
	
	@RequestMapping(value = "/category/update", method = RequestMethod.POST)
	public ModelAndView updateCategory(@ModelAttribute("editCategoryConfirm") CategoryForm categoryForm,
	        RedirectAttributes redirectAttributes) {
		this.categoryService.doUpdateCategory(categoryForm);
		ModelAndView mv = new ModelAndView("redirect:/category/list");
		redirectAttributes.addFlashAttribute("errorMsg", messageSource.getMessage("M_SC_CAT_0001", null, null));
		return mv;
	}
}