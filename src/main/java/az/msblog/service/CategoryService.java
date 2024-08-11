package az.msblog.service;

import az.msblog.dao.request.CategoryRequest;
import az.msblog.dao.response.CategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<CategoryResponse> getAllCategories();

    void createCategory(CategoryRequest categoryRequest);

    void updateCategory(Long id, CategoryRequest categoryRequest);

    void deleteCategory(Long id);
}
