package com.vipha.ecommerce.mapper;

import com.vipha.ecommerce.features.tag.Tag;
import com.vipha.ecommerce.features.tag.TagRequest;
import com.vipha.ecommerce.features.tag.TagResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

// covert interface into mapStruct
@Mapper(componentModel = "spring")
public interface TagMapper {

    Tag mapTagRequestToTag(TagRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntity(TagRequest dto, @MappingTarget Tag tag);

    TagResponse mapTagToTagResponse(Tag tag);
}
