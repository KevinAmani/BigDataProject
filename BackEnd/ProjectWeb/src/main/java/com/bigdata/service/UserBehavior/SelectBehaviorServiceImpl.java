package com.bigdata.service.UserBehavior;

import com.bigdata.model.UserBehavior.SelectBehavior;
import com.bigdata.repository.UserBehavior.SelectBehaviorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SelectBehaviorServiceImpl implements SelectBehaviorService {

    @Autowired
    private SelectBehaviorRepository selectBehaviorRepository;

    @Override
    public void saveSelectBehavior(SelectBehavior behavior){
        selectBehaviorRepository.save(behavior);
    }

}
