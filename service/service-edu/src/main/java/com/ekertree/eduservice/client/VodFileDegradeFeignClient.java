package com.ekertree.eduservice.client;

import com.baomidou.mybatisplus.extension.api.R;
import com.ekertree.commonutils.Result;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ClassName: VodFileDegradeFeignClient
 * Description:
 * date: 2022/7/14 23:18
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public Result removeAliYunVideo(String id) {
        return Result.error().setMessage("time out");
    }

    @Override
    public Result deleteBatch(List<String> videoIdList) {
        return Result.error().setMessage("time out");
    }
}
