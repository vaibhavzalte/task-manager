package com.uv.taskManager.service;

import com.uv.taskManager.entity.User;
import com.uv.taskManager.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class UserServiceTest {
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;
    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindByUserName() {
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("ram").password("sdfjsdkfskdf").roles(new ArrayList<>()).build());
        User user = userRepository.findByUserName("ram");
        Assertions.assertNotNull(user);
    }
}