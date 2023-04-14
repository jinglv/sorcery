package com.sorcery.platform.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jinglv
 * @date 2022/9/15 10:51
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Api(tags = "项目相关接口", value = "ProjectApi", protocols = "JSON")
public class ProjectApi {

    public void addProject() {
        
    }
}
