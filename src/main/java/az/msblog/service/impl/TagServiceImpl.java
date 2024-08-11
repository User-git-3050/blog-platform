package az.msblog.service.impl;

import az.msblog.dao.request.TagRequest;
import az.msblog.dao.response.TagResponse;
import az.msblog.entity.TagEntity;
import az.msblog.exceptions.NotFoundException;
import az.msblog.repository.TagRepository;
import az.msblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static az.msblog.enums.ErrorMessages.TAG_NOT_FOUND;
import static az.msblog.mapper.TagMapper.TAG_MAPPER;
import static java.lang.String.format;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<TagResponse> getAllTags() {
        return tagRepository.findAll().stream().map(TAG_MAPPER::mapToResponse).toList();
    }

    @Override
    public void createTag(TagRequest tagRequest) {
        tagRepository.save(TAG_MAPPER.mapToEntity(tagRequest));
    }

    @Override
    public void updateTag(Long id, TagRequest tagRequest) {
        TagEntity tagEntity = tagRepository.findById(id).
                orElseThrow(() -> new NotFoundException(
                        format(
                                TAG_NOT_FOUND.getMessage(),
                                id
                        )
                ));
        tagEntity.setName(tagRequest.getName());
        tagEntity.setDescription(tagRequest.getDescription());
        tagEntity.setModified(LocalDateTime.now());
        tagRepository.save(tagEntity);
    }

    @Override
    public void deleteTag(Long id) {
        TagEntity tagEntity = tagRepository.findById(id).
                orElseThrow(() -> new NotFoundException(
                        format(
                                TAG_NOT_FOUND.getMessage(),
                                id
                        )
                ));
        tagRepository.delete(tagEntity);
    }


}
