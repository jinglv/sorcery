package com.sorcery.platform.jenkins;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.util.StringUtils;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.QueueReference;
import com.sorcery.platform.dao.JenkinsTaskDAO;
import com.sorcery.platform.domain.JenkinsTask;
import com.sorcery.platform.domain.JsonResponse;
import com.sorcery.platform.exception.ConditionException;
import com.sorcery.platform.service.JenkinsService;
import com.sorcery.platform.utils.LocalUrlUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * @author jinglv
 * @date 2023/4/27 15:37
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JenkinsClient {

    private final JenkinsService jenkinsService;
    private final JenkinsTaskDAO jenkinsTaskDAO;
    private final JenkinsFactory jenkinsFactory;


    private final String PREFIX_TIPS = "调用Jenkins异常-";

    public JsonResponse<String> operateJenkinsJob(Long jenkinsId, Long jenkinsTaskId, String jenkinsName, String jenkinsBaseUrl, String token) throws IOException {
        log.info("Jenkins名称-{}", jenkinsName);
        // 获取ClassPATH下的（resources/jenkins/）下的job配置文件模板
        ClassPathResource resource = new ClassPathResource("jenkins/" + jenkinsName + ".xml");
        byte[] jenkinsData = FileCopyUtils.copyToByteArray(resource.getInputStream());
        String jobXml = new String(jenkinsData, StandardCharsets.UTF_8);
        log.info("解析Jenkins配置文件内容:{}", jobXml);
        if (ObjectUtils.isEmpty(jobXml)) {
            return JsonResponse.fail("Job配置信息不能为空");
        }
        // 查询Jenkins Task信息
        JenkinsTask jenkinsTask = jenkinsTaskDAO.getJenkinsTaskById(jenkinsTaskId);
        if (Objects.isNull(jenkinsTask)) {
            return JsonResponse.fail("Jenkins Job Id:" + jenkinsTaskId + "，Jenkins任务信息有误");
        }
        String jenkinsJobName = jenkinsTask.getJenkinsJobName();
        if (StringUtils.isNullOrEmpty(jenkinsJobName)) {
            log.info("新建Jenkins执行Job-Jenkins Id:{}, Jenkins Job Name:{}", jenkinsId, jenkinsJobName);
            createOrUpdateJob(jenkinsJobName, jobXml, jenkinsId);
        } else {
            log.info("更新Jenkins执行Job-Jenkins Id:{}, Jenkins Job Name:{}", jenkinsId, jenkinsJobName);
            createOrUpdateJob(jenkinsJobName, jobXml, jenkinsId);
        }
        String command = jenkinsTask.getCommand();
        if (StringUtils.isNullOrEmpty(command)) {
            throw new ConditionException("Jenkins任务执行命令不能为空");
        }
        // 获取本地URL
        String localUrl = LocalUrlUtil.getLocalUrl();
        StringBuilder updateStatusUrl = JenkinsUtils.getUpdateTaskStatusUrl(localUrl, token, jenkinsTaskId);
        Map<String, String> params = new ObjectMapper().readValue(command, new TypeReference<Map<String, String>>() {
        });
        params.put("updateStatusData", updateStatusUrl.toString());
        try {
            // 获取JenkinsServer
            JenkinsServer jenkinsServer = jenkinsFactory.getJenkinsServer(jenkinsId);
            // 获取对应的Job，并进行构建
            Job job = jenkinsFactory.getJenkinsJob(jenkinsJobName, jenkinsServer);
            build(job, params);
            return JsonResponse.success("成功");
        } catch (Exception e) {
            String tips = PREFIX_TIPS + "操作Jenkins的Job异常" + e.getMessage();
            log.error(tips, e);
            throw new ConditionException(tips);
        }
    }

    /**
     * 创建或更新Jenkins的Job
     *
     * @param jobName   job名称
     * @param jobXml    job xml配置文件
     * @param jenkinsId Jenkins主键id
     */
    public void createOrUpdateJob(String jobName, String jobXml, Long jenkinsId) {
        JenkinsServer jenkinsServer = jenkinsFactory.getJenkinsServer(jenkinsId);
        // 根据Jenkins任务查询Job Name是否存在，如果不存在，则进行新增，如果存在，则进行更新
        JenkinsTask jenkins = jenkinsTaskDAO.getJenkinsTaskByName(jobName);
        try {
            if (Objects.nonNull(jenkins)) {
                // Job不存在则创建Job
                jenkinsServer.createJob(null, jobName, jobXml, true);
            } else {
                // Job存在则更新Job
                jenkinsServer.updateJob(null, jobName, jobXml, true);
            }
        } catch (Exception e) {
            String tips = PREFIX_TIPS + "创建或更新Jenkins的Job时异常" + e.getMessage();
            log.error(tips, e);
            throw new ConditionException(tips);
        }
    }

    /**
     * 构建无参Jenkins Job
     *
     * @param job Jenkins Job
     * @return QueueReference
     * @throws IOException 抛出io异常
     */
    private QueueReference build(Job job) throws IOException {
        return build(job, null);
    }

    /**
     * 构建带参Jenkins Job
     *
     * @param job    Jenkins Job
     * @param params Jenkins构建传入的参数
     * @return QueueReference
     * @throws IOException 抛出io异常
     */
    private QueueReference build(Job job, Map<String, String> params) throws IOException {
        QueueReference queueReference;
        if (Objects.isNull(params)) {
            queueReference = job.build(true);
        } else {
            queueReference = job.build(params, true);
        }
        return queueReference;
    }
}
