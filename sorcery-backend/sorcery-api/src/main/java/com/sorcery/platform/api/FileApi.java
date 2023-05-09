package com.sorcery.platform.api;

import com.sorcery.platform.domain.JsonResponse;
import com.sorcery.platform.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author jinglv
 * @date 2022/9/16 17:47
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Api(tags = "文件相关接口", value = "FileApi", protocols = "JSON")
public class FileApi {

    private final FileUtils fileUtils;

    /**
     * 文件上传接口
     *
     * @param file 上传文件
     * @return 返回请求接口结果
     */
    @ApiOperation(value = "文件上传", notes = "文件上传接口")
    @PostMapping("/static/file")
    public JsonResponse<String> uploads(@RequestParam("file") MultipartFile file) {
        String url = fileUtils.uploads(file);
        return JsonResponse.success(url);
    }
}
