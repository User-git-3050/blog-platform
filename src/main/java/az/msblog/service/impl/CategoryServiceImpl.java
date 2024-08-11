package az.msblog.service.impl;

import az.msblog.dao.request.CategoryRequest;
import az.msblog.dao.response.CategoryResponse;
import az.msblog.entity.CategoryEntity;
import az.msblog.exceptions.NotFoundException;
import az.msblog.repository.CategoryRepository;
import az.msblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static az.msblog.enums.ErrorMessages.CATEGORY_NOT_FOUND;
import static az.msblog.mapper.CategoryMapper.*;
import static java.lang.String.format;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(CATEGORY_MAPPER::mapToResponse).toList();
    }

    @Override
    public void createCategory(CategoryRequest categoryRequest) {
        categoryRepository.save(CATEGORY_MAPPER.mapToCategory(categoryRequest));
    }

    @Override
    public void updateCategory(Long id, CategoryRequest categoryRequest) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).
                orElseThrow(() -> new NotFoundException(
                        format(
                                CATEGORY_NOT_FOUND.getMessage(),
                                id
                        )
                ));
        categoryEntity.setName(categoryRequest.getName());
        categoryEntity.setDescription(categoryRequest.getDescription());
        categoryEntity.setModified(LocalDateTime.now());
        categoryRepository.save(categoryEntity);
    }

    @Override
    public void deleteCategory(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).
                orElseThrow(() -> new NotFoundException(
                        format(
                                CATEGORY_NOT_FOUND.getMessage(),
                                id
                        )
                ));
        categoryRepository.delete(categoryEntity);
    }


}
