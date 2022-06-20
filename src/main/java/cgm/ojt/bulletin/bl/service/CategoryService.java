package cgm.ojt.bulletin.bl.service;

import java.util.List;

import cgm.ojt.bulletin.bl.dto.CategoryDto;
import cgm.ojt.bulletin.persistence.entity.Category;
import cgm.ojt.bulletin.web.form.CategoryForm;

public interface CategoryService {

	void doSaveCategory(CategoryForm categoryForm);

	List<Category> doGetAllCategory();

	CategoryDto doGetCategoryById(Integer categoryId);

	void doUpdateCategory(CategoryForm categoryForm);
}