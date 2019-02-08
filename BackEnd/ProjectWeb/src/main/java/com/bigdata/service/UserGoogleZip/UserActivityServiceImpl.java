package com.bigdata.service.UserGoogleZip;

import com.bigdata.model.UserGoogleZip.UserActivity;
import com.bigdata.repository.UserGoogleZip.UserActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserActivityServiceImpl implements UserActivityService {

    @Autowired
    private UserActivityRepository userActivityRepository;

    @Override
    public void saveUserActivity(UserActivity userActivity) {
        userActivityRepository.save(userActivity);
    }
}
