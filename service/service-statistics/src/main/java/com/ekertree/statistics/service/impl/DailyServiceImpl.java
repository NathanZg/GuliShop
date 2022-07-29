package com.ekertree.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ekertree.commonutils.DateUtil;
import com.ekertree.statistics.client.EduClient;
import com.ekertree.statistics.client.UcenterClient;
import com.ekertree.statistics.entity.Daily;
import com.ekertree.statistics.entity.vo.DailyQuery;
import com.ekertree.statistics.mapper.DailyMapper;
import com.ekertree.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author ekertree
 * @since 2022-07-26
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    private UcenterClient ucenterClient;
    private EduClient eduClient;
    private RedisTemplate<String,Object> redisTemplate;

    public DailyServiceImpl(UcenterClient ucenterClient, EduClient eduClient, RedisTemplate<String, Object> redisTemplate) {
        this.ucenterClient = ucenterClient;
        this.eduClient = eduClient;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void createStatistics(String day) {
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        Daily daily = baseMapper.selectOne(wrapper);
        Integer loginNum = (Integer) redisTemplate.opsForValue().get("loginNum");
        Integer countRegister = ucenterClient.countRegister(day);
        Integer videoViewNum = (Integer) redisTemplate.opsForValue().get("videoViewNum");
        Integer courseNum = eduClient.getCourseCount();
        if (daily == null) {
            daily = new Daily();
            daily.setDateCalculated(day);
            if(loginNum == null) {
                daily.setLoginNum(0);
            }else{
                daily.setLoginNum(loginNum);
            }
            if (videoViewNum == null) {
                daily.setVideoViewNum(0);
            }else{
                daily.setVideoViewNum(videoViewNum);
            }
            daily.setRegisterNum(countRegister);
            daily.setCourseNum(courseNum);
            baseMapper.insert(daily);
        }else{
            daily.setDateCalculated(day);
            if(loginNum != null) {
                daily.setLoginNum(loginNum);
            }
            if (videoViewNum != null) {
                daily.setVideoViewNum(videoViewNum);
            }
            daily.setRegisterNum(countRegister);
            daily.setCourseNum(courseNum);
            baseMapper.updateById(daily);
        }
        if (!DateUtil.formatDate(new Date()).equals(day)) {
            redisTemplate.delete("loginNum");
            redisTemplate.delete("videoViewNum");
        }
    }

    @Override
    public Map<String, Object> getChartData(DailyQuery dailyQuery) {
        createStatistics(DateUtil.formatDate(new Date()));
        String begin = dailyQuery.getBegin();
        String end = dailyQuery.getEnd();
        QueryWrapper<Daily> dayQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(begin)) {
            dayQueryWrapper.ge("date_calculated", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            dayQueryWrapper.le("date_calculated", end);
        }
        List<Daily> dayList = baseMapper.selectList(dayQueryWrapper);
        Map<String, Object> map = new HashMap<>();
        List<Integer> loginList = new ArrayList<Integer>();
        List<Integer> registerList = new ArrayList<Integer>();
        List<Integer> videoViewList = new ArrayList<Integer>();
        List<Integer> courseNumList = new ArrayList<Integer>();
        List<String> dateList = new ArrayList<String>();
        map.put("loginList", loginList);
        map.put("registerList", registerList);
        map.put("videoViewList", videoViewList);
        map.put("courseNumList", courseNumList);
        map.put("dateList", dateList);
        for (int i = 0; i < dayList.size(); i++) {
            Daily daily = dayList.get(i);
            dateList.add(daily.getDateCalculated());
            Integer loginNum = daily.getLoginNum();
            loginList.add(loginNum);
            Integer registerNum = daily.getRegisterNum();
            registerList.add(registerNum);
            Integer videoViewNum = daily.getVideoViewNum();
            videoViewList.add(videoViewNum);
            Integer courseNum = daily.getCourseNum();
            courseNumList.add(courseNum);
        }
        return map;
    }

    @Override
    public Map<String, Object> nowChart() {
        String day = DateUtil.formatDate(new Date());
        createStatistics(day);
        DailyQuery dailyQuery = new DailyQuery();
        dailyQuery.setBegin(day);
        dailyQuery.setEnd(day);
        Map<String, Object> map = getChartData(dailyQuery);
        return map;
    }

    @Override
    public void addLoginNum() {
        redisTemplate.opsForValue().increment("loginNum");
    }

    @Override
    public void addVideoView() {
        redisTemplate.opsForValue().increment("videoViewNum");
    }
}
