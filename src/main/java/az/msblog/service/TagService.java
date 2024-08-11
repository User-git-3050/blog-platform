package az.msblog.service;

import az.msblog.dao.request.TagRequest;
import az.msblog.dao.response.TagResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {
    List<TagResponse> getAllTags();

    void createTag(TagRequest tagRequest);

    void updateTag(Long id, TagRequest tagRequest);

    void deleteTag(Long id);
}
