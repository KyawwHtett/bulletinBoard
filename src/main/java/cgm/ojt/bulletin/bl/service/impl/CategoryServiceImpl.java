package cgm.ojt.bulletin.bl.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cgm.ojt.bulletin.bl.dto.CategoryDto;
import cgm.ojt.bulletin.bl.service.CategoryService;
import cgm.ojt.bulletin.persistence.dao.CategoryDao;
import cgm.ojt.bulletin.persistence.entity.Category;
import cgm.ojt.bulletin.web.form.CategoryForm;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	public void doSaveCategory(CategoryForm categoryForm) {
		Category category = new Category(categoryForm);
		this.categoryDao.dbSaveCategory(category);
	}

	@Override
	public List<Category> doGetAllCategory() {
		return this.categoryDao.dbGetAllCategory();
	}

	@Override
	public CategoryDto doGetCategoryById(Integer categoryId) {
		Category category = this.categoryDao.dbGetCategoryById(categoryId);
		CategoryDto categoryDto = new CategoryDto(category);
		return categoryDto;
	}

	@Override
	public void doUpdateCategory(CategoryForm categoryForm) {
		Category category = new Category(categoryForm);
		this.categoryDao.dbUpdateCategory(category);
	}
}