package cgm.ojt.bulletin.persistence.dao;

import java.util.List;

import cgm.ojt.bulletin.bl.dto.CategoryDto;
import cgm.ojt.bulletin.persistence.entity.Category;

public interface CategoryDao {

	void dbSaveCategory(Category category);

	List<Category> dbGetAllCategory();

	Category dbGetCategoryById(Integer categoryId);

	void dbUpdateCategory(Category category);
}