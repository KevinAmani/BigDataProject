package com.bigdata.service.UserBehavior;

import com.bigdata.model.UserBehavior.UrlBehavior;
import com.bigdata.repository.UserBehavior.UrlBehaviorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UrlBehaviorServiceImpl implements UrlBehaviorService {

    @Autowired
    private UrlBehaviorRepository urlBehaviorRepository;

    @Override
    public void saveUrlBehavior(UrlBehavior urlbehavior) {
        urlBehaviorRepository.save(urlbehavior);
    }
}
