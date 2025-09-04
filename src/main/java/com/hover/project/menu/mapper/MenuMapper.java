package com.hover.project.menu.mapper;

import com.hover.project.menu.dao.MenuDao;
import com.hover.project.menu.entity.Menu;
import com.hover.project.menu.request.AddMenuRequest;
import com.hover.project.menu.request.UpdateMenuRequest;
import com.hover.project.menu.response.MenuResponse;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class MenuMapper {

    @Autowired
    private MenuDao menuDao;

    public abstract Menu mapToEntityFromAddMenuRequest(AddMenuRequest addMenuRequest);

    public abstract MenuResponse mapToMenuResponseFromEntity(Menu menu);

    public abstract void updateMenuFromDto(UpdateMenuRequest updateMenuRequest,@MappingTarget Menu menu);

    public abstract List<MenuResponse> mapToMenuResponses(List<Menu> menus);



}
