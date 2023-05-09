package com.sorcery.platform.jenkins;

import cn.hutool.json.JSONUtil;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.Job;
import com.sorcery.platform.dao.JenkinsDAO;
import com.sorcery.platform.domain.JenkinsInfo;
import com.sorcery.platform.exception.ConditionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;

/**
 * @author jinglv
 * @date 2023/4/27 10:42
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JenkinsFactory {

    private final JenkinsDAO jenkinsDAO;

    /**
     * 根据Jenkins Job Name获取Jenkins Job
     *
     * @param jobName       Jenkins Job Name
     * @param jenkinsServer JenkinsServer
     * @return Job
     */
    public Job getJenkinsJob(String jobName, JenkinsServer jenkinsServer) {
        Job job;
        try {
            Map<String, Job> jobs = jenkinsServer.getJobs();
            job = jobs.get(jobName);
        } catch (IOException e) {
            log.error("获取Jenkins当前Job失败！");
            throw new ConditionException(e);
        }
        return job;
    }

    /**
     * 获取Jenkins的客户端JenkinsServer
     *
     * @param jenkinsId Jenkins id
     * @return JenkinsServer
     */
    public JenkinsServer getJenkinsServer(Long jenkinsId) {
        return new JenkinsServer(getJenkinsHttpClient(jenkinsId));
    }

    /**
     * 获取Jenkins的客户端JenkinsClient
     *
     * @param jenkinsId Jenkins id
     * @return JenkinsHttpClient
     */
    public JenkinsHttpClient getJenkinsHttpClient(Long jenkinsId) {
        JenkinsHttpClient jenkinsHttpClient;
        try {
            JenkinsInfo jenkins = getJenkins(jenkinsId);
            log.info("根据Jenkins Id:{}, 查询Jenkins信息：{}", jenkinsId, JSONUtil.parse(jenkins));
            jenkinsHttpClient = new JenkinsHttpClient(new URI(jenkins.getJenkinsUrl()), jenkins.getJenkinsUsername(), jenkins.getJenkinsPassword());
        } catch (URISyntaxException e) {
            log.error("获取Jenkins服务异常-{}", e.getMessage());
            throw new ConditionException(e.getMessage());
        }
        return jenkinsHttpClient;
    }

    /**
     * 根据Jenkins id查询Jenkins信息
     *
     * @param jenkinsId Jenkins Id
     * @return Jenkins信息
     */
    public JenkinsInfo getJenkins(Long jenkinsId) {
        JenkinsInfo jenkins = jenkinsDAO.getJenkinsById(jenkinsId);
        if (Objects.isNull(jenkins)) {
            throw new ConditionException("未查询到Jenkins信息！");
        }
        return jenkins;
    }

}
