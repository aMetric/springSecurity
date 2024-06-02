package com.coderwhs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coderwhs.entity.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(Long id);
}
